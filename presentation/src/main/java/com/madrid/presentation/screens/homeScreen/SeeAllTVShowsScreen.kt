package com.madrid.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.FilterBar
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTVShowsViewModel
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTvShowType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SeeAllTVShowsScreen(
    type: SeeAllTvShowType,
    viewModel: SeeAllTVShowsViewModel = koinViewModel { parametersOf(type) }
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val items = uiState.genre

    var selectedItem by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        if (uiState.genre.isNotEmpty()) {
            val firstGenre = uiState.selectedGenre
            if (firstGenre != null) {
                selectedItem = firstGenre
            }
            if (selectedItem.isNotEmpty())
                viewModel.onGenreSelect(uiState.genre.find { it.name == selectedItem })
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            TopAppBar(uiState.title, secondIcon = null, thirdIcon = null , onFirstIconClick = {
                navController.popBackStack()})
        }
        item(span = { GridItemSpan(maxLineSpan) }) {

            val updatedItems: MutableList<String> = items.map { it.name }.toMutableList()
            updatedItems.add(0, "All")
            FilterBar(
                items = updatedItems,
                selectedItem = selectedItem,
                onItemClick = { genre ->
                    selectedItem = genre
                    viewModel.onGenreSelect(
                        if (selectedItem != "All") items.find { it.name == genre } else null
                    )
                },
                scrollable = true
            )

        }

        items(uiState.filteredSeries.size) { index ->
            val movie = uiState.filteredSeries[index]
            MovioVerticalCard(
                description = movie.name,
                movieImage = movie.imageUrl,
                rate = movie.rate.take(3),
                width = 130.dp,
                height = 200.dp,
                onClick = {
                    navController.navigate(
                        Destinations.SeriesDetailsScreen(
                            uiState.filteredSeries[index].id.toInt(),
                            seasonNumber = 1
                        )
                    )
                }
            )
        }
    }
}


@Preview
@Composable
private fun TopRatingScreenPreview(modifier: Modifier = Modifier) {
//    SeeAllTVShowsScreen()
}