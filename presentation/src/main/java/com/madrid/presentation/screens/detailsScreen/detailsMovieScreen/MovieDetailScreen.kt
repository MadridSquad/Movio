package com.madrid.presentation.screens.detailsScreen.detailsMovieScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.component.BottomSheet.AddToListBottomSheetContent
import com.madrid.designSystem.component.BottomSheet.UserList
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastSection
import com.madrid.presentation.component.header.MovieDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.designSystem.component.ButtomSheet.RatingBottomSheetContent
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.componant.ExpandableDescription
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.toReviewScreenUiState
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMoviesSection
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import org.koin.androidx.compose.koinViewModel
import com.madrid.designSystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    viewModel: DetailsMovieViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val showRatingBottomSheet = remember { mutableStateOf(false) }
    val showAddToListBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    Box(

        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Theme.color.surfaces.surfaceContainer)
    ) {
        MoviePosterDetailScreen(
            imageUrl = uiState.topImageUrl,
            modifier = Modifier.fillMaxSize()
        )
        TopAppBar(
            text = null,
            modifier = Modifier.padding(start = 16.dp, top = 36.dp),
            onFirstIconClick = { navController.navigate(Destinations.SearchScreen) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(360.dp))
            MovieDetailsHeader(
                movieName = uiState.movieName,
                movieCategory = uiState.genreMovie,
                date = uiState.dataMovie,
                time = uiState.movieDuration,
                rate = uiState.rate.take(3),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            BottomMediaActions(
                onRateClick = {
                    showRatingBottomSheet.value = true
                },
                onPlayClick = {},
                onAddToListClick = { showAddToListBottomSheet.value = true },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            ExpandableDescription(
                description = uiState.description,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            TopCastSection(
                castMembers = uiState.casts.map { cast ->
                    CastMember(
                        id = cast.id.toString(),
                        name = cast.name,
                        imageUrl = cast.imageUrl
                    )
                },
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.TopCast(
                            mediaId = uiState.movieId,
                            isMovie = true
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            if(uiState.reviews.isNotEmpty()){
                ReviewScreen(
                    onSeeAllReviews = {
                        navController.navigate(
                            Destinations.ReviewsScreen(
                                uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    uiState = uiState.reviews.toReviewScreenUiState()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            SimilarMoviesSection(
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SimilarMediaScreen(
                            mediaId = uiState.movieId,
                            isMovie = true
                        )
                    )
                },
                onMovieClick = { movie ->
                    navController.navigate(
                        Destinations.MovieDetailsScreen(
                            movieId = movie.id
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                similarMovies = uiState.similarMovies.map { movie ->
                    SimilarMovie(
                        id = movie.id,
                        title = movie.title,
                        imageUrl = movie.imageUrl,
                        rating = movie.rating
                    )
                }
            )
        }
        if (showRatingBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showRatingBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                RatingBottomSheetContent(
                    movieTitle = uiState.movieName,
                    moviePosterResId = R.drawable.library_main_icon,
                    onRatingSubmitted = { rating -> Log.d("Rating", "Submitted rating: $rating") }
                )
            }
        }
        if (showAddToListBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showAddToListBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                AddToListBottomSheetContent(
                    onListCreated = {
                        Log.d("AddToList", "Create new list clicked")
                    },
                    initialUserLists = listOf(
                        UserList("1", "Watch later", isSelected = true),
                        UserList("2", "Favorites", isSelected = false)
                        // Add more initial lists as needed
                    ),
                    onSelectionChanged = { userList, isSelected -> Log.d("AddToList", "Selection changed: ${userList.name} -> $isSelected") }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailsScreenPreview() {
    MovieDetailsScreen()
}