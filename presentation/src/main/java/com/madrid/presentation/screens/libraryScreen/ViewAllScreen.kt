package com.madrid.presentation.screens.libraryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.component.SwipeToDeleteCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllViewModel
import com.madrid.presentation.viewModel.shared.MediaType

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
            text = state.title,
            modifier = Modifier,
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { interactionListener.onBackClicked() }
        )

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
                count = state.items.size,
                key = { index -> state.items[index].id }
            ) { index ->
                val item = state.items[index]

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
}