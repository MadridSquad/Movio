package com.madrid.presentation.screens.libraryScreen

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.CustomHorizontalCardForWatchList
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.CreateListBottomSheet
import com.madrid.presentation.screens.addtolist.SuccessNotificationRow
import com.madrid.presentation.screens.libraryScreen.component.LibraryScreenHeader
import com.madrid.presentation.screens.refreshScreenHolder.RefreshScreenHolder
import com.madrid.presentation.viewModel.libraryViewModel.LibraryInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.LibraryScreenEffect
import com.madrid.presentation.viewModel.libraryViewModel.LibraryScreenState
import com.madrid.presentation.viewModel.libraryViewModel.LibraryViewModel
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import kotlinx.coroutines.flow.collectLatest
import com.madrid.presentation.R as presentationR

@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = hiltViewModel()
) {
    val state = libraryViewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        libraryViewModel.loadData()
    }

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
                            watchListId = effect.watchListId,
                            watchListTitle = effect.watchListTitle
                        )
                    )
                }

                is LibraryScreenEffect.NavigateWatchListToViewAll -> {
                    navController.navigate(
                        Destinations.WatchListViewAllScreen
                    )
                }

                is LibraryScreenEffect.NavigateToViewAll -> {
                    navController.navigate(
                        Destinations.ViewAllScreen(
                            type = effect.type,
                        )
                    )
                }

                is LibraryScreenEffect.NavigateToLogin -> {
                    navController.navigate(
                        Destinations.LoginScreen
                    )
                }
            }
        }
    }
    RefreshScreenHolder(
        refreshState = state.refreshState,
        onRefresh = { libraryViewModel.onRefresh() }
    ) {
        LibraryScreenContent(
            state = state,
            libraryInteractionListener = libraryViewModel as LibraryInteractionListener,
        )
    }
}

@Composable
private fun LibraryScreenContent(
    state: LibraryScreenState,
    libraryInteractionListener: LibraryInteractionListener
) {
    AnimatedVisibility(
        visible = state.isGuest,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoginLayout(interactionListener = libraryInteractionListener)
    }

    AnimatedVisibility(
        visible = state.isGuest.not() && state.isLoading.not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LibraryColumn(state, libraryInteractionListener)
    }

    CreateListSection(
        onDismissSnackBar = libraryInteractionListener::onDismissSnackBar,
        onCreateButtonClicked = libraryInteractionListener::onCreateWatchListButtonClicked,
        dismissCreateListBottomSheet = libraryInteractionListener::dismissCreateListBottomSheet,
        showSnackBar = state.isSnackBarVisible,
        snackBarMessage = state.snackBarMessage,
        showCreateListBottomSheet = state.isCreateListBottomSheetVisible
    )
}

@Composable
private fun LibraryColumn(
    state: LibraryScreenState,
    libraryInteractionListener: LibraryInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
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
                onSeeAllClick = libraryInteractionListener::onWatchListViewAllClick,
                onWatchListClick = libraryInteractionListener::onItemWatchListClick,
                onEmptyWatchListClick = libraryInteractionListener::onAddWatchListClicked
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
                onSeeAllClick = { libraryInteractionListener.onViewAllClick(ViewAllType.FAVORITES) },
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
                onSeeAllClick = { libraryInteractionListener.onViewAllClick(ViewAllType.HISTORY) },
                onMediaClickWithId = libraryInteractionListener::onItemClick
            )
        }
    }
}


@Composable
private fun LoginLayout(
    interactionListener: LibraryInteractionListener,
) {
    DialogWithButtonLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
        title = stringResource(presentationR.string.unlock_your_personal_library),
        description = stringResource(presentationR.string.access_your_watch_history_favorites_and_watchlist_all_in_one_place),
        image = R.drawable.library_main_icon,
        buttonText = stringResource(presentationR.string.login),
        onClick = { interactionListener.onLoginBtnClick() },
    )
}

@Composable
private fun CreateListSection(
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