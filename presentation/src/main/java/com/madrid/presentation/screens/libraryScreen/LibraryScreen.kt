package com.madrid.presentation.screens.libraryScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.CustomHorizontalCardForWatchList
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.libraryScreen.component.LibraryScreenHeader
import com.madrid.presentation.viewModel.libraryViewModel.LibraryInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.LibraryScreenEffect
import com.madrid.presentation.viewModel.libraryViewModel.LibraryScreenState
import com.madrid.presentation.viewModel.libraryViewModel.LibraryViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = hiltViewModel()
) {

    val state = libraryViewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current


    LaunchedEffect(libraryViewModel.effect) {
        libraryViewModel.effect.collectLatest { effect ->
            when (effect) {
                is LibraryScreenEffect.NavigateToMediaDetails -> {
                    navController.navigate(
                        Destinations.MovieDetailsScreen(
                            movieId = effect.mediaId.toInt(),
                        )
                    )
                }

                is LibraryScreenEffect.NavigateToWatchListDetails -> {
                     navController.navigate(
                         Destinations.WatchListDetailsScreen(
                             watchListId =  effect.watchListId,
                             watchListTitle =effect.watchListTitle
                         )
                     )
                }

                is LibraryScreenEffect.NavigateToViewAll -> {
                    libraryViewModel.onViewAllClick(effect.type)
                }
            }
        }
    }

    LibraryScreenContent(
        state = state,
        libraryInteractionListener = libraryViewModel as LibraryInteractionListener,
    )
}

@Composable
private fun LibraryScreenContent(
    onClickWatchListViewAll: () -> Unit = {},
    onClickFavoriteListViewAll: () -> Unit = {},
    onClickHistoryListViewAll: () -> Unit = {},
    state: LibraryScreenState,
    libraryInteractionListener: LibraryInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
    ) {
        item {
            LibraryScreenHeader(stringResource(com.madrid.presentation.R.string.Library))
        }
        item {
            CustomHorizontalCardForWatchList(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.watchlist),
                listOfWatch = state.watchList,
                modifier = Modifier,
                headerModifier = Modifier.padding(horizontal = 16.dp),
                startIconForPrimaryTextTitle = painterResource(R.drawable.outline_minimalistic),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.view_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = onClickWatchListViewAll,
                onWatchListClick = libraryInteractionListener::onItemWatchListClick
            )
        }
        item {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.favorites),
                listOfMedia = state.favoriteList,
                modifier = Modifier,
                headerModifier = Modifier.padding(horizontal = 16.dp),
                startIconForPrimaryTextTitle = painterResource(R.drawable.outline_heart),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.view_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = onClickFavoriteListViewAll,
                onMediaClickWithId = libraryInteractionListener::onItemClick
            )
        }
        item {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.history),
                listOfMedia = state.historyList,
                modifier = Modifier,
                headerModifier = Modifier.padding(horizontal = 16.dp),
                startIconForPrimaryTextTitle = painterResource(R.drawable.outline_history),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.view_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = onClickHistoryListViewAll,
                onMediaClickWithId = libraryInteractionListener::onItemClick
            )
        }
    }
}