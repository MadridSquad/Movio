package com.madrid.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.component.HeaderSectionBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.header.HomeAppBar
import com.madrid.presentation.screens.homeScreen.layout.AllMediaLayout
import com.madrid.presentation.screens.homeScreen.layout.CategoriesLayout
import com.madrid.presentation.screens.homeScreen.layout.MoviesLayout
import com.madrid.presentation.screens.homeScreen.layout.TvShowsLayout
import com.madrid.presentation.viewModel.homeViewModel.HomeInteractionListener
import com.madrid.presentation.viewModel.homeViewModel.HomeScreenEffect
import com.madrid.presentation.viewModel.homeViewModel.HomeScreenState
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val state by homeViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeScreenEffect.NavigateToMediaDetails -> {
                    //TODO()
                }

                is HomeScreenEffect.NavigateToProfile -> TODO()
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
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .padding(top = 32.dp),
    ) {
        LayoutContent(HomeTab.entries[selectedTabIndex])
        Column {
            HomeAppBar(modifier = Modifier.padding(horizontal = 16.dp))
            HeaderSectionBar(
                tabs = listOf(
                    stringResource(R.string.all),
                    stringResource(R.string.Movies),
                    stringResource(R.string.TV_Shows),
                    stringResource(R.string.Categories),
                ),
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun LayoutContent(
    selectedTab: HomeTab,
    state: HomeScreenState,
    interactionListener: HomeInteractionListener
) {
    when (selectedTab) {
        HomeTab.ALL -> AllMediaLayout()
        HomeTab.MOVIES -> MoviesLayout()
        HomeTab.TV_SHOWS -> TvShowsLayout()
        HomeTab.CATEGORIES -> CategoriesLayout(
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
        )
    }
}

enum class HomeTab {
    ALL,
    MOVIES,
    TV_SHOWS,
    CATEGORIES
}