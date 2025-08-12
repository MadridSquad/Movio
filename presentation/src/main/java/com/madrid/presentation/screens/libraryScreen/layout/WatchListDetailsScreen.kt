package com.madrid.presentation.screens.libraryScreen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.component.SwipeToDeleteCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            TopAppBar(
                text = state.headerTitle,
                secondIcon = null,
                thirdIcon = null,
                onFirstIconClick = interaction::onNavigateBack,
            )
        }
        items(
            state.watchList.size, key = { index -> state.watchList[index].id }
        ) { index ->
            val watchList = state.watchList[index]
            SwipeToDeleteCard(
                title = watchList.title,
                movieRate = watchList.rating,
                movieCategory = watchList.category.first().name,
                movieImageUrl = watchList.imageUrl,
                onDelete = { interaction.onDeleteMovie(watchList.id) },
                onClick = { interaction.onClickMovieItem(watchList.id) },
            )
        }
    }
}