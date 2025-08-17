package com.madrid.presentation.screens.libraryScreen.watchList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.R
import com.madrid.presentation.component.SwipeToDeleteCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.SuccessNotificationRow
import com.madrid.presentation.screens.libraryScreen.component.LoadingContent
import com.madrid.presentation.viewModel.libraryViewModel.layout.WatchListDetailsEffect
import com.madrid.presentation.viewModel.libraryViewModel.layout.WatchListDetailsInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.layout.WatchListDetailsState
import com.madrid.presentation.viewModel.libraryViewModel.layout.WatchListDetailsViewModel

@Composable
fun WatchListDetailsScreen(
    viewModel: WatchListDetailsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                WatchListDetailsEffect.NavigateBack -> navController.popBackStack()
                is WatchListDetailsEffect.NavigateToMovieDetails -> navController.navigate(
                    Destinations.MovieDetailsScreen(effect.movieId.toInt())
                )
            }
        }
    }
    WatchListDetailsScreenContent(
        state = state,
        interaction = viewModel as WatchListDetailsInteractionListener
    )
}

@Composable
private fun WatchListDetailsScreenContent(
    state: WatchListDetailsState,
    interaction: WatchListDetailsInteractionListener
) {
    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .statusBarsPadding(),
        text = state.headerTitle,
        secondIcon = null,
        thirdIcon = null,
        onFirstIconClick = interaction::onNavigateBack,
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        AnimatedVisibility(state.isLoading, enter = fadeIn(), exit = fadeOut()) {
            LoadingContent()
        }

        AnimatedVisibility(
            state.errorMessage.isNullOrBlank().not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            DialogWithButtonLayout(
                title = stringResource(R.string.empty_no_internet_title),
                description = stringResource(R.string.empty_no_internet_description),
                image = R.drawable.img_no_internet,
                buttonText = stringResource(R.string.try_again),
                onClick = interaction::onClickTryAgainButton,
                imageSize = 150,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
            )
        }

        AnimatedVisibility(
            state.isLoading.not() && state.mediaItems.isEmpty() && state.errorMessage.isNullOrBlank(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EmptySearchLayout(
                title = stringResource(R.string.empty_list_message, state.headerTitle),
                description = stringResource(R.string.empty_list_description),
                image = R.drawable.empty_list,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp,),
                imageSize = 180,
            )
        }

        AnimatedVisibility(
            state.isLoading.not() && state.mediaItems.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = state.mediaItems.size, key = { index -> state.mediaItems[index].id }
                ) { index ->
                    val watchList = state.mediaItems[index]
                    SwipeToDeleteCard(
                        title = watchList.title,
                        movieRate = watchList.rating,
                        movieCategory = watchList.category.firstOrNull()?.name?: "",
                        movieImageUrl = watchList.imageUrl,
                        onDelete = { interaction.onDeleteMovie(watchList.id) },
                        onClick = { interaction.onClickMovieItem(watchList.id) },
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        SuccessNotificationRow(
            isVisible = state.isSnackBarVisible,
            message = stringResource(R.string.Item_has_been_deleted),
            actionText = stringResource(R.string.undo),
            onAction = { interaction.onClickUndoAction() },
            onDismiss = interaction::onDismissSnackBar,
            icon = painterResource(com.madrid.designSystem.R.drawable.archive_tick),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        )
    }
}