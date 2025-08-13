package com.madrid.presentation.screens.libraryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.FloatingButton
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.component.videoLibrary.VideoLibrary
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.CreateListBottomSheet
import com.madrid.presentation.screens.addtolist.ListBottomSheetMode
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchListViewAllInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllViewModel

@Composable
fun WatchlistViewAllScreen(
    viewModel: WatchlistViewAllViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is WatchlistViewAllEffect.NavigateBack -> {
                    navController.navigateUp()
                }

                is WatchlistViewAllEffect.NavigateToDetails -> {
                    navController.navigate(
                        Destinations.WatchListDetailsScreen(
                            watchListId = effect.watchListId,
                            watchListTitle = effect.watchListTitle
                        )
                    )
                }
            }
        }

    }

    val interactionListener = viewModel as WatchListViewAllInteractionListener
    WatchlistViewAllScreenContent(
        interactionListener = interactionListener,
        state = state
    )
}

@Composable
fun WatchlistViewAllScreenContent(
    interactionListener: WatchListViewAllInteractionListener,
    state: WatchlistViewAllUiState
) {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        TopAppBar(
            text = "Watchlist",
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { interactionListener.onBackButtonClicked() },
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        if (state.watchLists.isEmpty()){
            EmptySearchLayout(
                title = "\uD83C\uDFAC Nothing here yet!",
                description = "Add movies and TV shows to build your personal watchlist. The perfect binge starts here!",
                image = R.drawable.empty,
                imageSize = 180,
                modifier = Modifier.fillMaxSize()
            )
        }else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 158.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.watchLists.size) { index ->
                    val watchlist = state.watchLists[index]
                    VideoLibrary(
                        onClick = { interactionListener.onItemClick(watchlist) },
                        videosNumber = watchlist.numberOfVideos,
                        title = watchlist.watchListTitle,
                        posterUrl = watchlist.posterUrl
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FloatingButton(
            onClick = interactionListener::onAddButtonClicked,
            size = 60,
            icon = painterResource(id = R.drawable.add),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }

    CreateListBottomSheet(
        show = state.showCreateListBottomSheet,
        onCreateClick = interactionListener::onCreateButtonClicked,
        onDismiss = interactionListener::dismissCreateListBottomSheet,
    )
}