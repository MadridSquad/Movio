package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.MovioPager
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.modifier.ShimmerCard
import com.madrid.presentation.modifier.ShimmerHorizontalCard
import com.madrid.presentation.modifier.ShimmerItem
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesType
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MoviesLayout(
    trendingMovies: List<MediaUiState>,
    topRatingMovies: List<MediaUiState>,
    nowPlayingMovies: List<MediaUiState>,
    upComingMovies: List<MediaUiState>,
    recommendedMovies: List<MediaUiState>,
    onScroll: (Boolean) -> Unit = {},
    isLoading: Boolean = true,
    isSliderLoading: Boolean,
    isTopRatedLoading: Boolean,
    isNowPlayingLoading: Boolean,
    isUpComingLoading: Boolean,
    isRecommendedLoading: Boolean,
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
        columns = GridCells.Adaptive(minSize = 168.dp),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {

            ShimmerItem(isLoading = isSliderLoading) {
                MovioPager(
                    medias = trendingMovies.take(7),
                    onClickItem = { id -> navController.navigate(Destinations.MovieDetailsScreen(id)) },
                    onClickMediaButton = { mediaIndex -> onClickMediaButton(mediaIndex) },
                    isLoading = isLoading
                )
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {

            ShimmerHorizontalCard(
                isLoading = isTopRatedLoading,
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.top_rating),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.TOP_RATING)) },
                headerModifier = Modifier.padding(horizontal = 16.dp),
                itemCount = 5
            ) {
                CustomHorizontalCard(
                    primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.top_rating),
                    secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                    endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                    listOfMedia = topRatingMovies,
                    onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.TOP_RATING)) },
                    onMediaClick = { mediaUiState ->
                        navController.navigate(Destinations.MovieDetailsScreen(mediaUiState.id.toInt()))
                    },
                    headerModifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }


        item(span = { GridItemSpan(maxLineSpan) }) {
            ShimmerHorizontalCard(
                isLoading = isNowPlayingLoading,
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.now_playing),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.NOW_PLAYING)) },
                headerModifier = Modifier.padding(horizontal = 16.dp),
                itemCount = 5
            ) {
                CustomHorizontalCard(
                    primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.now_playing),
                    secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                    endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                    listOfMedia = nowPlayingMovies,
                    onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.NOW_PLAYING)) },
                    onMediaClick = { mediaUiState ->
                        navController.navigate(Destinations.MovieDetailsScreen(mediaUiState.id.toInt()))
                    },
                    headerModifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
                )
            }
        }


        item(span = { GridItemSpan(maxLineSpan) }) {
            ShimmerHorizontalCard(
                isLoading = isUpComingLoading,
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.upcoming),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.UPCOMING)) },
                headerModifier = Modifier.padding(horizontal = 16.dp),
                itemCount = 5
            ) {
                CustomHorizontalCard(
                    primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.upcoming),
                    secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                    endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                    listOfMedia = upComingMovies,
                    onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.UPCOMING)) },
                    onMediaClick = { mediaUiState ->
                        navController.navigate(Destinations.MovieDetailsScreen(mediaUiState.id.toInt()))
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
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.MORE_RECOMMENDED)) },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }

        if (isRecommendedLoading) {
            items(9) {
                ShimmerCard(
                    isLoading = true,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(150.dp)
                        .height(220.dp)
                )
            }
        } else {
            itemsIndexed(recommendedMovies) { index, media ->
                MovioVerticalCard(
                    description = media.title,
                    movieImage = media.imageUrl,
                    rate = media.rating.take(3),
                    height = 220.dp,
                    onClick = { navController.navigate(Destinations.MovieDetailsScreen(media.id.toInt())) },
                    modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                )
            }
        }
    }
}