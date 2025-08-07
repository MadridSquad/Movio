package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.MovioPager
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTvShowType
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun TvShowsLayout(
    trendingSeries: List<MediaUiState>,
    topRatingSeries: List<MediaUiState>,
    airingTodaySeries: List<MediaUiState>,
    onAirSeries: List<MediaUiState>,
    recommendedSeries: List<MediaUiState>,
    onScroll: (Boolean) -> Unit ={},
    ) {
    val navController = LocalNavController.current
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            lazyGridState.firstVisibleItemScrollOffset + (lazyGridState.firstVisibleItemIndex * 1000)
        }
            .distinctUntilChanged()
            .collect { scrollOffset ->
                val scrollDistance = scrollOffset.toFloat()
                when(scrollDistance){
                    in 0f..100f -> onScroll(false)
                    else -> onScroll(true)
                }

            }
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            MovioPager(
                medias = trendingSeries.take(7),
            )
        }
        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.top_rating),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = topRatingSeries,
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.TOP_RATING
                        )
                    )
                },
                onMediaClick = { mediaUiState ->
                    navController.navigate(
                        Destinations.SeriesDetailsScreen(
                            mediaUiState.id.toInt(),
                            1
                        )
                    )
                },
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.airing_today),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = airingTodaySeries,
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.AIRING_TODAY
                        )
                    )
                },
                onMediaClick = { mediaUiState ->
                    navController.navigate(
                        Destinations.SeriesDetailsScreen(
                            mediaUiState.id.toInt(),
                            1
                        )
                    )
                },
                headerModifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }

        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.on_tv),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = onAirSeries,
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.ON_TV
                        )
                    )
                },
                onMediaClick = { mediaUiState ->
                    navController.navigate(
                        Destinations.SeriesDetailsScreen(
                            mediaUiState.id.toInt(),
                            1
                        )
                    )
                },
                headerModifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }

        item(span = { GridItemSpan(2) }) {
            CustomTextTitle(
                primaryText = stringResource(com.madrid.presentation.R.string.more_recommended),
                secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
                endIcon = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.MORE_RECOMMENDED
                        )
                    )
                },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }

        itemsIndexed(recommendedSeries) { index, media ->
            var endPaddingValue = 0
            var startPaddingValue = 0

            if (index % 2 == 0)
                startPaddingValue = 16
            else
                endPaddingValue = 16
            MovioVerticalCard(
                description = media.title,
                movieImage = media.imageUrl,
                rate = media.rating.take(3),
                height = 220.dp,
                onClick = {
                    navController.navigate(
                        Destinations.SeriesDetailsScreen(
                            media.id.toInt(),
                            1
                        )
                    )
                },
                modifier = Modifier.padding(start = startPaddingValue.dp, end = endPaddingValue.dp)
            )
        }
    }
}