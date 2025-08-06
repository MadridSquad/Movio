package com.madrid.presentation.screens.detailsScreen.seriesDetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.CustomTextTitle
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
import com.madrid.presentation.component.header.SeriesDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.component.movioCards.MovioSeasonCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeries
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeriesSection
import com.madrid.presentation.viewModel.detailsViewModel.ReviewUiState
import com.madrid.presentation.viewModel.detailsViewModel.ReviewsScreenUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val seasons = uiState.currentSeasonsUiStates
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Series Link", text)
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

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptySearchLayout(
                title = stringResource(R.string.internet_is_not_available),
                description = stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                image = R.drawable.img_no_internet
            )
        }
    } else {
        MovioBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false },
            containerColor = Theme.color.surfaces.surface
        ) {
            ShareBottomSheetContent(
                onCopyLink = {
                    copyToClipboard("https://www.themoviedb.org/tv/${uiState.seriesId}")
                    showSheet = false
                },
                onShareFacebook = {
                    shareToApp(
                        "com.facebook.katana",
                        "https://www.themoviedb.org/tv/${uiState.seriesId}"
                    )
                    showSheet = false
                },
                onShareX = {
                    shareToApp(
                        "com.twitter.android",
                        "https://www.themoviedb.org/tv/${uiState.seriesId}"
                    )
                    showSheet = false
                }
            )
        }

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
                onFirstIconClick = { navController.popBackStack() },
                onSecondIconClick = { showSheet = true } // Share action
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(360.dp))
                SeriesDetailsHeader(
                    movieName = uiState.seriesName,
                    seriesCategory = uiState.seriesGenre,
                    date = uiState.productionDate,
                    time = stringResource(id = R.string.season_count, uiState.numberOfSeasons),
                    rate = uiState.rate.take(3),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                BottomMediaActions(
                    onRateClick = {},
                    onPlayClick = {},
                    onAddToListClick = {},
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextWithReadMore(
                    description = uiState.description,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp),
                    maxLines = 5
                )

                TopCastHorizontalScroll(
                    castMembers = uiState.topCast.map { cast ->
                        CastMember(
                            id = cast.id.toString(),
                            name = cast.name,
                            imageUrl = cast.imageUrl
                        )
                    },
                    onSeeAllClick = {
                        navController.navigate(
                            Destinations.TopCast(
                                mediaId = uiState.seriesId,
                                isMovie = false
                            )
                        )
                    },
                    onCastMemberClick = { castId ->
                        navController.navigate(
                            Destinations.ActorDetails(
                                artistId = castId
                            )
                        )
                    }
                )

                CustomTextTitle(
                    primaryText = stringResource(R.string.current_seasons),
                    secondaryText = stringResource(R.string.see_all),
                    endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
                    onSeeAllClick = {
                        navController.navigate(
                            Destinations.SeasonsScreen(
                                uiState.seriesId,
                                1
                            )
                        )
                    },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 12.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(seasons) { _, season ->
                        MovioSeasonCard(
                            movieTitle = "",
                            movieImage = season.imageUrl,
                            movieRate = season.rate,
                            totalNumberOfEpisodes = season.numberOfEpisodes.toString(),
                            onClick = {
                                navController.navigate(
                                    Destinations.EpisodesScreen(
                                        seriesId = uiState.seriesId,
                                        seasonNumber = season.seasonNumber
                                    )
                                )
                            },
                            yearOfPublish = season.productionDate,
                            currentSeason = season.seasonNumber.toString(),
                            timeOfPublish = season.productionDate
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (uiState.reviews.isNotEmpty()) {
                    ReviewScreen(
                        onSeeAllReviews = {
                            navController.navigate(
                                Destinations.ReviewsScreen(
                                    uiState.seriesId,
                                    isMovie = false
                                )
                            )
                        },
                        uiState = uiState.reviews.toReviewScreenUiState()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                if (uiState.similarSeries.isNotEmpty()) {
                    Log.d(
                        "in series details screen",
                        "SeriesDetailsScreen: ${uiState.similarSeries}"
                    )
                    SimilarSeriesSection(
                        similarSeries = uiState.similarSeries.map { series ->
                            SimilarSeries(
                                id = series.id,
                                title = series.name,
                                imageUrl = series.imageUrl,
                                rating = series.rate.take(3).toDoubleOrNull() ?: 0.0
                            )
                        },
                        onSeeAllClick = {
                            navController.navigate(
                                Destinations.SimilarMediaScreen(
                                    mediaId = uiState.seriesId,
                                    isMovie = false
                                )
                            )
                        },
                        onSeriesClick = { series ->
                            navController.navigate(
                                Destinations.SeriesDetailsScreen(
                                    seriesId = series.id,
                                    1
                                )
                            )
                        },
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

fun List<ReviewUiState>.toReviewScreenUiState(): ReviewsScreenUiState {
    return ReviewsScreenUiState(reviews = this)
}
