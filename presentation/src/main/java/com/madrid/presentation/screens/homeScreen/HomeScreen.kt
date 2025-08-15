package com.madrid.presentation.screens.homeScreen

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.component.HeaderSectionBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.header.HomeAppBar
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.homeScreen.layout.CategoriesLayout
import com.madrid.presentation.screens.homeScreen.layout.MoviesLayout
import com.madrid.presentation.screens.homeScreen.layout.TvShowsLayout
import com.madrid.presentation.viewModel.homeViewModel.CategoryUiState
import com.madrid.presentation.viewModel.homeViewModel.HomeInteractionListener
import com.madrid.presentation.viewModel.homeViewModel.HomeScreenEffect
import com.madrid.presentation.viewModel.homeViewModel.HomeScreenState
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel
import com.madrid.presentation.viewModel.homeViewModel.SortingType
import com.madrid.presentation.viewModel.shared.MediaType

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeScreenEffect.NavigateToMediaDetails -> {
                    if (effect.mediaType == MediaType.MOVIE) {
                        navController.navigate(Destinations.MovieDetailsScreen(effect.mediaId))
                    } else {
                        navController.navigate(Destinations.SeriesDetailsScreen(effect.mediaId, 1))
                    }
                }

                is HomeScreenEffect.NavigateToProfile -> navController.navigate(Destinations.MoreScreen)
                is HomeScreenEffect.GoToYoutube -> {
                    val key = effect.trailerKey
                    openYoutubeMediaTrailer(key = key, context = context)
                }
            }
        }
    }
    HomeScreenContent(state = state, homeViewModel)
}


@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    interactionListener: HomeInteractionListener
) {
    var isScrolledDown by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        if (isScrolledDown) Theme.color.surfaces.surface else Color.Transparent,
        tween(500)
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
    ) {
        LayoutContent(
            uiState = state,
            onScroll = { isScrolled ->
                isScrolledDown = isScrolled
            },
            onClickMediaButton = { mediaType, mediaIndex ->
                interactionListener.onClickPlayButton(
                    mediaIndex = mediaIndex,
                    mediaType = mediaType,
                )
            },
            onSelectCategory = interactionListener::onSelectCategory,
            onSelectSortingType = interactionListener::onSelectSortingType,
            onMediaSelected = interactionListener::onMediaSelected
        )
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(top = 32.dp)
        ) {
            HomeAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                image = state.profileImage,
                onClickIcon = { interactionListener.onClickProfile() }
            )
            HeaderSectionBar(
                tabs = listOf(
                    stringResource(R.string.Movies),
                    stringResource(R.string.TV_Shows),
                    stringResource(R.string.Categories),
                ),
                selectedTabIndex = state.selectedTabIndex,
                onTabSelected = { index ->
                    interactionListener.onSelectTab(index)
                },
                modifier = Modifier.padding(horizontal = 38.dp)
            )
        }
    }
}

@Composable
private fun LayoutContent(
    uiState: HomeScreenState,
    onScroll: (Boolean) -> Unit = {},
    onClickMediaButton: (MediaType, Int) -> Unit = { _, _ -> },
    onSelectCategory: (CategoryUiState) -> Unit = {},
    onSelectSortingType: (SortingType) -> Unit = {},
    onMediaSelected: (Int, MediaType) -> Unit = { _, _ -> },
) {
    when (uiState.selectedTabIndex) {
        0 -> LoadMoviesLayout(
            uiState = uiState,
            onScroll = onScroll,
            onClickMediaButton = onClickMediaButton
        )

        1 -> LoadSeriesLayout(
            uiState = uiState,
            onScroll = onScroll,
            onClickMediaButton = onClickMediaButton
        )

        2 -> LoadCategoriesLayout(
            uiState = uiState,
            onSelectCategory = onSelectCategory,
            onSelectSortingType = onSelectSortingType,
            onMediaSelected = onMediaSelected
        )
    }
}

@Composable
private fun LoadMoviesLayout(
    uiState: HomeScreenState,
    onScroll: (Boolean) -> Unit = {},
    onClickMediaButton: (MediaType, Int) -> Unit = { _, _ -> },
) {
    MoviesLayout(
        trendingMovies = uiState.movieTabUiState.trending.media,
        topRatingMovies = uiState.movieTabUiState.topRated.media,
        nowPlayingMovies = uiState.movieTabUiState.nowPlaying.media,
        upComingMovies = uiState.movieTabUiState.upcoming.media,
        recommendedMovies = uiState.movieTabUiState.moreRecommended.media,
        onScroll = onScroll,
        onClickMediaButton = { mediaIndex ->
            onClickMediaButton(
                MediaType.MOVIE,
                mediaIndex
            )
        },
        isLoading = uiState.isLoading,
        isSliderLoading = uiState.movieTabUiState.trending.isLoading,
        isTopRatedLoading = uiState.movieTabUiState.topRated.isLoading,
        isNowPlayingLoading = uiState.movieTabUiState.nowPlaying.isLoading,
        isUpComingLoading = uiState.movieTabUiState.upcoming.isLoading,
        isRecommendedLoading = uiState.movieTabUiState.moreRecommended.isLoading,
    )
}

@Composable
fun LoadSeriesLayout(
    uiState: HomeScreenState,
    onScroll: (Boolean) -> Unit = {},
    onClickMediaButton: (MediaType, Int) -> Unit = { _, _ -> },
) {
    TvShowsLayout(
        trendingSeries = uiState.tvShowTabUiState.trending.media,
        topRatingSeries = uiState.tvShowTabUiState.topRated.media,
        airingTodaySeries = uiState.tvShowTabUiState.airingToday.media,
        onAirSeries = uiState.tvShowTabUiState.onTv.media,
        recommendedSeries = uiState.tvShowTabUiState.moreRecommended.media,
        onScroll = onScroll,
        onClickMediaButton = { mediaIndex ->
            onClickMediaButton(
                MediaType.TV_SHOW,
                mediaIndex
            )
        },
        isLoading = uiState.isLoading,
        isTrendingSeriesLoading = uiState.tvShowTabUiState.trending.isLoading,
        isTopRatedSeriesLoading = uiState.tvShowTabUiState.topRated.isLoading,
        isOnAirSeriesLoading = uiState.tvShowTabUiState.onTv.isLoading,
        isAiringTodaySeriesLoading = uiState.tvShowTabUiState.airingToday.isLoading,
        isRecommendedSeriesLoading = uiState.tvShowTabUiState.moreRecommended.isLoading
    )
}

@Composable
private fun LoadCategoriesLayout(
    uiState: HomeScreenState,
    onSelectCategory: (CategoryUiState) -> Unit = {},
    onSelectSortingType: (SortingType) -> Unit = {},
    onMediaSelected: (Int, MediaType) -> Unit = { _, _ -> },
){
    CategoriesLayout(
        categories = uiState.categoryTabUiState.categories,
        selectedCategory = uiState.categoryTabUiState.selectedCategory,
        onCategorySelected = { category -> onSelectCategory(category) },
        mediaItems = uiState.categoryTabUiState.media.collectAsLazyPagingItems(),
        sortingType = uiState.categoryTabUiState.sortingType,
        onSortingTypeSelected = { sortingType ->
            onSelectSortingType(sortingType)
        },
        onMediaItemClicked = onMediaSelected,
    )
}

private fun openYoutubeMediaTrailer(key: String, context: Context) {
    if (key.isNotBlank()) {
        val youtubeAppIntent =
            Intent(Intent.ACTION_VIEW, "vnd.youtube:$key".toUri())
        val youtubeWebIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.youtube.com/watch?v=$key".toUri()
        )

        try {
            context.startActivity(youtubeAppIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(youtubeWebIntent)
        }
    }
}

enum class HomeTab {
    MOVIES,
    TV_SHOWS,
    CATEGORIES
}