package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.MovioPager
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.designSystem.modifier.ShimmerCard
import com.madrid.designSystem.modifier.ShimmerHorizontalCard
import com.madrid.designSystem.modifier.ShimmerItem
import com.madrid.designSystem.modifier.removeWidthPaddingFromParent
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
    onScroll: (Boolean) -> Unit = {},
    isLoading: Boolean = true,
    isTrendingSeriesLoading: Boolean,
    isTopRatedSeriesLoading: Boolean,
    isAiringTodaySeriesLoading: Boolean,
    isRecommendedSeriesLoading: Boolean,
    isOnAirSeriesLoading: Boolean,
    onClickMediaButton: (Int) -> Unit = {},
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
                when (scrollDistance) {
                    in 0f..100f -> onScroll(false)
                    else -> onScroll(true)
                }

            }
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 158.dp),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ShimmerItem(
                modifier = Modifier.removeWidthPaddingFromParent(16.dp),
                isLoading = isTrendingSeriesLoading
            ) {
                MovioPager(
                    medias = trendingSeries.take(7),
                    onClickItem = { id ->
                        navController.navigate(
                            Destinations.SeriesDetailsScreen(
                                id,
                                1
                            )
                        )
                    },
                    onClickMediaButton = onClickMediaButton,
                    isLoading = isLoading,
                    modifier = Modifier.removeWidthPaddingFromParent(16.dp),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .offset(y = 390.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Theme.color.surfaces.surface)
                            )
                        )
                        .removeWidthPaddingFromParent(16.dp)
                )
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            ShimmerHorizontalCard(
                modifier = Modifier.removeWidthPaddingFromParent(16.dp),
                isLoading = isTopRatedSeriesLoading,
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.top_rating),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.TOP_RATING
                        )
                    )
                },
                headerModifier = Modifier.padding(horizontal = 16.dp),
                itemCount = 20
            ) {
                CustomHorizontalCard(
                    modifier = Modifier.removeWidthPaddingFromParent(16.dp),
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
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            ShimmerHorizontalCard(
                modifier = Modifier.removeWidthPaddingFromParent(16.dp),
                isLoading = isAiringTodaySeriesLoading,
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.airing_today),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.AIRING_TODAY
                        )
                    )
                },
                headerModifier = Modifier.padding(horizontal = 16.dp),
                itemCount = 20
            ) {
                CustomHorizontalCard(
                    modifier = Modifier.removeWidthPaddingFromParent(16.dp),
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
        }

        item(span = { GridItemSpan(maxLineSpan) }) {

            ShimmerHorizontalCard(
                modifier = Modifier.removeWidthPaddingFromParent(16.dp),
                isLoading = isOnAirSeriesLoading,
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.on_tv),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = {
                    navController.navigate(
                        Destinations.SeeAllTvShowsScreen(
                            SeeAllTvShowType.ON_TV
                        )
                    )
                },
                headerModifier = Modifier.padding(horizontal = 16.dp),
                itemCount = 20
            ) {
                CustomHorizontalCard(
                    modifier = Modifier.removeWidthPaddingFromParent(16.dp),
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
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
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
                modifier = Modifier.padding(top = 20.dp)
            )
        }

        if (isRecommendedSeriesLoading) {
            items(10) {
                ShimmerCard(
                    isLoading = true,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(150.dp)
                        .height(220.dp)
                        .removeWidthPaddingFromParent(16.dp)
                )
            }
        } else {
            itemsIndexed(recommendedSeries) { index, media ->
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
                )
            }
        }
    }
}