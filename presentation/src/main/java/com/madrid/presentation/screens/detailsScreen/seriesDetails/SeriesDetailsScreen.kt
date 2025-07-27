package com.madrid.presentation.screens.detailsScreen.seriesDetails

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastSection
import com.madrid.presentation.component.header.MovieDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.component.movioCards.MovioSeasonCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.componant.ExpandableDescription
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeries
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeriesSection
import com.madrid.presentation.viewModel.detailsViewModel.ReviewUiState
import com.madrid.presentation.viewModel.detailsViewModel.ReviewsScreenUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val seasons = uiState.currentSeasonsUiStates
    val artists = uiState.topCast
    Log.d("TAG lol", "SeriesDetailsScreen: sizeeeeeeeeeeeeee: ${artists.size}")

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
                movieName = uiState.seriesName,
                movieCategory = uiState.seriesGenre,
                date = uiState.productionDate,
                time = "${uiState.numberOfSeasons} Seasons",
                rate = uiState.rate.take(3),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            BottomMediaActions(
                onRateClick = {},
                onPlayClick = {},
                onAddToListClick = {},
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            ExpandableDescription(
                description = uiState.description,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            TopCastSection(
                castMembers = uiState.topCast.map { cast ->
                    CastMember(
                        id = cast.id.toString(),
                        name = cast.name,
                        imageUrl = cast.imageUrl
                    )
                },
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.TopCastScreen(
                            mediaId = uiState.seriesId,
                            isMovie = false
                        )
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioText(
                    text = "ٍCurrent Seasons",
                    color = Theme.color.surfaces.onSurface,
                    textStyle = Theme.textStyle.headline.mediumMedium18
                )

                MovioText(
                    text = "${stringResource(id = R.string.see_all)} >",
                    color = Theme.color.surfaces.onSurfaceVariant,
                    textStyle = Theme.textStyle.label.smallRegular14,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Destinations.SeasonsScreen(
                                uiState.seriesId,
                                1
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(seasons) { index, season ->
                    MovioSeasonCard(
                        movieTitle = season.seasonNumber.toString(),
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
                        currentSeason = (index + 1).toString(),
                        timeOfPublish = season.productionDate
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            if(uiState.reviews.isNotEmpty()){
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
            SimilarSeriesSection(
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
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                similarSeries = uiState.similarSeries.map { series ->
                    SimilarSeries(
                        id = series.id,
                        title = series.name,
                        imageUrl = series.imageUrl,
                        rating = (series.rate.take(3)).toDouble()
                    )

                }
            )
        }
    }
}

fun List<ReviewUiState>.toReviewScreenUiState(): ReviewsScreenUiState {
    return ReviewsScreenUiState(reviews = this)
}

