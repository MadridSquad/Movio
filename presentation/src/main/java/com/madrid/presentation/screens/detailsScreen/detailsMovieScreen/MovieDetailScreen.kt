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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.TextWithReadMore
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastSection
import com.madrid.presentation.component.header.MovieDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.toReviewScreenUiState
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMoviesSection
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    viewModel: DetailsMovieViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val casts = uiState.casts
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
            modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
            onFirstIconClick = {
                navController.popBackStack() }
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
                onRateClick = {},
                onPlayClick = {},
                onAddToListClick = {},
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextWithReadMore(
                description = uiState.description,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                maxLines = 5
            )
            TopCastSection(
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.TopCast(
                            mediaId = uiState.movieId,
                            isMovie = true
                        )
                    )
                },
                onCastMemberClick = { castId ->
                    navController.navigate(
                        Destinations.ActorDetails(
                            artistId = castId
                        )
                    )
                },
                modifier = Modifier.padding( vertical = 8.dp),
                  castMembers = uiState.casts.map { cast ->
                    CastMember(
                        id = cast.id.toString(),
                        name = cast.name,
                        imageUrl = cast.imageUrl
                    )
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (uiState.reviews.isNotEmpty()) {
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
                Spacer(modifier = Modifier.height(24.dp))
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
                modifier = Modifier.padding(vertical = 8.dp),
                similarMovies = uiState.similarMovies.map { movie ->
                    SimilarMovie(
                        id = movie.id,
                        title = movie.title,
                        imageUrl = movie.imageUrl,
                        rating = movie.rating
                    )
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}