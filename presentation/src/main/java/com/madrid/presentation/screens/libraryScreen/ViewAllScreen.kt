package com.madrid.presentation.screens.libraryScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.component.SwipeToDeleteCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.SuccessNotificationRow
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllViewModel
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
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
                    navController.navigate(
                        Destinations.LibraryScreen
                    )
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
            text = state.title,
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
            visible = state.isLoading.not() && state.items.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EmptyListContent(
                title = state.title,
                description = stringResource(state.emptyListMessage)
            )
        }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.items.isEmpty().not(),
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
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                vertical = 16.dp,
                horizontal = 16.dp
            )
        ) {
            items(5) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
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
        image = R.drawable.no_internet,
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
        image = R.drawable.empty,
        imageSize = 180,
        modifier = Modifier.fillMaxSize()
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
                movieCategory = item.category.first().name,
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
            actionText = "Undo",
            icon = painterResource(id = R.drawable.archive_tick),
            onDismiss = interactionListener::onDismissSnackBar,
            onAction = interactionListener::onUndoDeleteClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        )
    }
}


private fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(1000)),
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF2A2445),
                Color(0xFF3D3660),
                Color(0xFF2A2445),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}