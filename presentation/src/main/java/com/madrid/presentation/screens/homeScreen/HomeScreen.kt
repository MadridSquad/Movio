package com.madrid.presentation.screens.homeScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.madrid.presentation.viewModel.homeViewModel.HomeInteractionListener
import com.madrid.presentation.viewModel.homeViewModel.HomeScreenEffect
import com.madrid.presentation.viewModel.homeViewModel.HomeScreenState
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel
import com.madrid.presentation.viewModel.shared.MediaType

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val navController = LocalNavController.current

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
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var isScrolledDown by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if(isScrolledDown) Theme.color.surfaces.surface else Color.Transparent , tween(500))
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
    ) {
        LayoutContent(
            uiState = state,
            selectedTab = HomeTab.entries[selectedTabIndex],
            onClickMoviesTab = interactionListener::loadMoviesLayoutData,
            onClickSeriesTab = interactionListener::loadSeriesLayoutData,
            onScroll = { isScrolled ->
                isScrolledDown = isScrolled
            }
        )
        Column(modifier = Modifier
            .background(backgroundColor)
            .padding(top = 32.dp)) {
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
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                },
                modifier = Modifier.padding(horizontal = 38.dp)
            )

            if (HomeTab.CATEGORIES == HomeTab.entries[selectedTabIndex])
                CategoriesLayout(
                    categories = state.categoryTabUiState.categories,
                    selectedCategory = state.categoryTabUiState.selectedCategory,
                    onCategorySelected = { category -> interactionListener.onSelectCategory(category) },
                    mediaItems = state.categoryTabUiState.media.collectAsLazyPagingItems(),
                    sortingType = state.categoryTabUiState.sortingType,
                    onSortingTypeSelected = { sortingType ->
                        interactionListener.onSelectSortingType(
                            sortingType
                        )
                    },
                    onMediaItemClicked = interactionListener::onMediaSelected,
                )
        }
    }
}

@Composable
private fun LayoutContent(
    selectedTab: HomeTab,
    uiState: HomeScreenState,
    onClickMoviesTab: () -> Unit,
    onClickSeriesTab: () -> Unit,
    onScroll: (Boolean) -> Unit ={},
    ) {
    when (selectedTab) {
//        HomeTab.ALL -> AllMediaLayout()
        HomeTab.MOVIES -> {
            onClickMoviesTab()
            MoviesLayout(
                trendingMovies = uiState.movieTabUiState.trending.media,
                topRatingMovies = uiState.movieTabUiState.topRated.media,
                nowPlayingMovies = uiState.movieTabUiState.nowPlaying.media,
                upComingMovies = uiState.movieTabUiState.upcoming.media,
                recommendedMovies = uiState.movieTabUiState.moreRecommended.media,
                onScroll = onScroll
            )
        }

        HomeTab.TV_SHOWS -> {
            onClickSeriesTab()
            TvShowsLayout(
                trendingSeries = uiState.tvShowTabUiState.trending.media,
                topRatingSeries = uiState.tvShowTabUiState.topRated.media,
                airingTodaySeries = uiState.tvShowTabUiState.airingToday.media,
                onAirSeries = uiState.tvShowTabUiState.onTv.media,
                recommendedSeries = uiState.tvShowTabUiState.moreRecommended.media,
                onScroll = onScroll
            )
        }

        else -> {}
    }
}

enum class HomeTab {
//    ALL,
    MOVIES,
    TV_SHOWS,
    CATEGORIES
}