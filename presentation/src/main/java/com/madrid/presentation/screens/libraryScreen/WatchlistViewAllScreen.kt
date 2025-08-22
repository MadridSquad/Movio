package com.madrid.presentation.screens.libraryScreen

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.FloatingButton
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.component.shimmerEffect
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.videoLibrary.VideoLibrary
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.CreateListBottomSheet
import com.madrid.presentation.screens.addtolist.SuccessNotificationRow
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchListViewAllInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllViewModel
import com.madrid.presentation.R as presentationR

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
            text = stringResource(presentationR.string.watchlist),
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { interactionListener.onBackButtonClicked() },
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) { LoadingContent() }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.errorMessage.isNullOrBlank().not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ErrorContent(
                onClick = interactionListener::onTryAgainButtonClicked
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.errorMessage.isNullOrBlank() && state.watchLists.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EmptyListContent()
            CreateListSection(
                onAddButtonClicked = interactionListener::onAddButtonClicked,
                onDismissSnackBar = interactionListener::onDismissSnackBar,
                onCreateButtonClicked = interactionListener::onCreateButtonClicked,
                dismissCreateListBottomSheet = interactionListener::dismissCreateListBottomSheet,
                showSnackBar = state.showSnackBar,
                snackBarMessage = state.snackBarMessage,
                showCreateListBottomSheet = state.showCreateListBottomSheet
            )
        }
        AnimatedVisibility(
            visible = state.isLoading.not() && state.errorMessage.isNullOrBlank() && state.watchLists.isEmpty().not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            WatchListsGrid(interactionListener, state)
            CreateListSection(
                onAddButtonClicked = interactionListener::onAddButtonClicked,
                onDismissSnackBar = interactionListener::onDismissSnackBar,
                onCreateButtonClicked = interactionListener::onCreateButtonClicked,
                dismissCreateListBottomSheet = interactionListener::dismissCreateListBottomSheet,
                showSnackBar = state.showSnackBar,
                snackBarMessage = state.snackBarMessage,
                showCreateListBottomSheet = state.showCreateListBottomSheet
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 158.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
        ) {
            items(12) { index ->
                Box(
                    modifier = Modifier
                        .size(158.dp, 220.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .shimmerEffect(),
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(onClick: () -> Unit = {}) {
    DialogWithButtonLayout(
        title = stringResource(presentationR.string.internet_is_not_available),
        description = stringResource(presentationR.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
        image = Theme.drawables.noInternetId,
        imageSize = 150,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp)
            .fillMaxSize(),
        buttonText = stringResource(presentationR.string.try_again),
        onClick = { onClick() }
    )
}

@Composable
private fun EmptyListContent() {
    EmptySearchLayout(
        title = stringResource(presentationR.string.nothing_here_yet),
        description = stringResource(presentationR.string.add_movies_and_tv_shows_to_build_your_personal_watchlist), //stringResource(presentationR.string.no_results_found),
        image = Theme.drawables.emptyLayoutId,
        imageSize = 180,
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    )
}

@Composable
private fun WatchListsGrid(
    interactionListener: WatchListViewAllInteractionListener,
    state: WatchlistViewAllUiState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 158.dp),
        verticalArrangement = Arrangement.Top,
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

@Composable
private fun CreateListSection(
    onAddButtonClicked: () -> Unit,
    onDismissSnackBar: () -> Unit,
    onCreateButtonClicked: (String) -> Unit,
    dismissCreateListBottomSheet: () -> Unit,
    showSnackBar: Boolean,
    @StringRes snackBarMessage: Int,
    showCreateListBottomSheet: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FloatingButton(
            onClick = onAddButtonClicked,
            size = 60,
            icon = painterResource(id = R.drawable.add),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
        SuccessNotificationRow(
            isVisible = showSnackBar,
            message = stringResource(snackBarMessage),
            icon = painterResource(id = R.drawable.archive_tick),
            onDismiss = onDismissSnackBar,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
    }
    CreateListBottomSheet(
        show = showCreateListBottomSheet,
        onCreateClick = onCreateButtonClicked,
        onDismiss = dismissCreateListBottomSheet,
    )
}