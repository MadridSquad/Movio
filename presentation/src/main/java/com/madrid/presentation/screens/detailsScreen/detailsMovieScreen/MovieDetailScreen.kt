package com.madrid.presentation.screens.detailsScreen.detailsMovieScreen

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.ShareBottomSheetContent
import com.madrid.designSystem.component.TextWithReadMore
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.component.header.MovieDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.toReviewScreenUiState
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMoviesSection
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel


@Composable
fun MovieDetailsScreen(
    viewModel: DetailsMovieViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Movie Link", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show()
    }

    fun shareToApp(appPackage: String, url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            setPackage(appPackage)
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val fallback = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
            }
            context.startActivity(Intent.createChooser(fallback, "Share via"))
        }
    }

    val casts = uiState.casts

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptySearchLayout(
                title = stringResource(R.string.internet_is_not_available),
                description =
                    stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                image = R.drawable.img_no_internet
            )
        }
    } else {

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

            MovioBottomSheet(
                show = showSheet,
                onDismiss = { showSheet = false },
                containerColor = Theme.color.surfaces.surface
            ) {
                ShareBottomSheetContent(
                    onCopyLink = {
                        copyToClipboard("https://www.themoviedb.org/movie/${uiState.movieId}")
                        showSheet = false
                    },
                    onShareFacebook = {
                        shareToApp(
                            "com.facebook.katana",
                            "https://www.themoviedb.org/movie/${uiState.movieId}"
                        )
                        showSheet = false
                    },
                    onShareX = {
                        shareToApp(
                            "com.twitter.android",
                            "https://www.themoviedb.org/movie/${uiState.movieId}"
                        )
                        showSheet = false
                    }
                )
            }

            TopAppBar(
                text = null,
                modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
                onFirstIconClick = { navController.popBackStack() },
                onSecondIconClick = { showSheet = true }
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
                    onPlayClick = {
                        uiState.trailerKey?.let { key ->
                            val youtubeAppIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
                            val youtubeWebIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/watch?v=$key")
                            )

                            try {
                                context.startActivity(youtubeAppIntent)
                            } catch (e: ActivityNotFoundException) {
                                context.startActivity(youtubeWebIntent)
                            }
                        }
                    },
                    onAddToListClick = {},
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextWithReadMore(
                    description = uiState.description,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    maxLines = 5
                )
                TopCastHorizontalScroll(
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
                    modifier = Modifier.padding(top = 32.dp),
                    castMembers = uiState.casts.map { cast ->
                        CastMember(
                            id = cast.id.toString(),
                            name = cast.name,
                            imageUrl = cast.imageUrl
                        )
                    }
                )
                ReviewScreen(
                    modifier = Modifier.padding(top = 32.dp),
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
                SimilarMoviesSection(
                    modifier = Modifier.padding(vertical = 32.dp),
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
}