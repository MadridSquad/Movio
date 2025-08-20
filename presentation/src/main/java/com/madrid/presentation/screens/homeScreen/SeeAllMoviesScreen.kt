package com.madrid.presentation.screens.homeScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.FilterBar
import com.madrid.designSystem.component.LoadingSearchCard
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SeeAllMoviesScreen(
    viewModel: SeeAllMoviesViewModel
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val items = uiState.genre
    val listOfItem = uiState.filteredMovies.collectAsLazyPagingItems()

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
    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        TopAppBar(
            uiState.title,
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { navController.navigate(Destinations.HomeScreen) },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
        )
        Spacer(Modifier.height(16.dp))
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
        Spacer(Modifier.height(24.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 101.33.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.surfaces.surface)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            when {
                listOfItem.itemCount == 0 && listOfItem.loadState.refresh is LoadState.Loading -> {
                    items(9) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingSearchCard()
                        }
                    }
                }

                listOfItem.itemCount > 0 -> {
                    items(
                        count = listOfItem.itemCount,
                    ) { index ->
                        BoxWithConstraints {
                            val cardWidth = maxWidth
                            val cardHeight = cardWidth * (136f / 101.33f)
                            val movie = listOfItem[index]
                            MovioVerticalCard(
                                modifier = Modifier.width(cardWidth),
                                description = movie?.name
                                    ?: stringResource(R.string.no_results_found),
                                movieImage = movie?.imageUrl
                                    ?: "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
                                rate = movie?.rate?.take(3) ?: "4.5",
                                imageHeight = cardHeight,
                                onClick = {
                                    navController.navigate(
                                        Destinations.MovieDetailsScreen(
                                            listOfItem[index]!!.id.toInt(),
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = listOfItem.itemCount == 0 && listOfItem.loadState.refresh is LoadState.Error,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            DialogWithButtonLayout(
                title = stringResource(R.string.empty_no_internet_title),
                description = stringResource(R.string.empty_no_internet_description),
                image = R.drawable.img_no_internet,
                buttonText = stringResource(R.string.try_again),
                onClick = { viewModel.onTryAgainClick() },
                imageSize = 170,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
            )
        }

        AnimatedVisibility(
            visible = listOfItem.itemCount == 0 && listOfItem.loadState.refresh is LoadState.NotLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EmptySearchLayout(
                title = stringResource(R.string.nothing_here_yet),
                description = stringResource(R.string.category_empty_description),
                image = R.drawable.img_no_sesrch_found,
                imageSize = 170,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
            )
        }
    }
}