package com.madrid.presentation.screens.libraryScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.SwipeToDeleteCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.component.addtolist.SuccessNotificationRow
import com.madrid.presentation.screens.libraryScreen.component.LoadingContent
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllViewModel
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlin.collections.firstOrNull
import com.madrid.presentation.R as presentationR

@Composable
fun ViewAllScreen(
    viewModel: ViewAllViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ViewAllEffect.NavigateBack -> {
                    navController.navigateUp()
                }

                is ViewAllEffect.NavigateToDetails -> {
                    if (effect.mediaType == MediaType.MOVIE) {
                        navController.navigate(
                            Destinations.MovieDetailsScreen(movieId = effect.mediaId.toInt())
                        )
                    } else if (effect.mediaType == MediaType.TV_SHOW) {
                        navController.navigate(
                            Destinations.SeriesDetailsScreen(
                                seriesId = effect.mediaId.toInt(),
                                seasonNumber = 1 // Default to season 1, can be changed later
                            )
                        )
                    }
                }
            }
        }
    }

    ViewAllScreenContent(
        state = state,
        interactionListener = viewModel as ViewAllInteractionListener
    )
}

@Composable
fun ViewAllScreenContent(
    state: ViewAllUiState,
    interactionListener: ViewAllInteractionListener
) {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        TopAppBar(
            text = stringResource(state.title),
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { interactionListener.onBackClicked() },
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
            visible = state.errorMessage.isNullOrBlank().not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ErrorContent(
                onClick = interactionListener::onTryAgainButtonClicked
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.errorMessage.isNullOrBlank() && state.items.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EmptyListContent(
                title = stringResource(state.title),
                description = stringResource(state.emptyListMessage)
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.errorMessage.isNullOrBlank() && state.items.isEmpty()
                .not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ShowItemsColumn(
                items = state.items,
                interactionListener = interactionListener
            )
        }
    }
    UndoSnackBar(
        showSnackBar = state.showSnackBar,
        snackBarMessage = state.snackBarMessage,
        interactionListener = interactionListener
    )
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
private fun EmptyListContent(title: String, description: String) {
    EmptySearchLayout(
        title = stringResource(presentationR.string.empty_list_message, title),
        description = description,
        image = Theme.drawables.emptyLayoutId,
        imageSize = 180,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun ShowItemsColumn(
    items: List<MediaUiState>,
    interactionListener: ViewAllInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            vertical = 16.dp,
            horizontal = 16.dp
        )
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id }
        ) { index ->
            val item = items[index]

            SwipeToDeleteCard(
                title = item.title,
                movieRate = item.rating,
                movieCategory = item.category.firstOrNull()?.name ?: "",
                movieImageUrl = item.imageUrl,
                onDelete = {
                    interactionListener
                        .onItemDeleted(item.id, item.mediaType)
                },
                onClick = {
                    interactionListener
                        .onItemClicked(item.id, item.mediaType)
                },
            )
        }
    }
}

@Composable
private fun UndoSnackBar(
    showSnackBar: Boolean,
    snackBarMessage: Int,
    interactionListener: ViewAllInteractionListener
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SuccessNotificationRow(
            isVisible = showSnackBar,
            message = stringResource(snackBarMessage),
            actionText = stringResource(presentationR.string.undo),
            icon = painterResource(id = R.drawable.archive_tick),
            onDismiss = interactionListener::onDismissSnackBar,
            onAction = interactionListener::onUndoDeleteClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        )
    }
}