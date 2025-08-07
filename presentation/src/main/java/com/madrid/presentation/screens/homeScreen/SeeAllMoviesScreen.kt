package com.madrid.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.component.FilterBar
import com.madrid.designSystem.component.LoadingSearchCard
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.MovioVerticalGrid
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesViewModel
import com.madrid.presentation.viewModel.shared.MediaUiState

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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 102.dp),
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
            TopAppBar(uiState.title, secondIcon = null, thirdIcon = null, onFirstIconClick = { navController.popBackStack()})
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

            listOfItem.itemCount == 0 && listOfItem.loadState.refresh is LoadState.Error -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MovioText(
                            text = stringResource(com.madrid.presentation.R.string.internet_is_not_available),
                            textStyle = Theme.textStyle.title.mediumMedium16,
                            color = Theme.color.surfaces.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        MovioText(
                            text = stringResource(com.madrid.presentation.R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                            textStyle = Theme.textStyle.label.smallRegular12,
                            color = Theme.color.surfaces.onSurfaceContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )

                    }
                }
            }

            listOfItem.itemCount == 0 && listOfItem.loadState.refresh is LoadState.NotLoading && listOfItem.loadState.refresh.endOfPaginationReached -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MovioText(
                            text = "No results found",
                            textStyle = Theme.textStyle.title.mediumMedium16,
                            color = Theme.color.surfaces.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        MovioText(
                            text = stringResource(com.madrid.presentation.R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                            textStyle = Theme.textStyle.label.smallRegular12,
                            color = Theme.color.surfaces.onSurfaceContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )

                    }
                }
            }

            listOfItem.itemCount > 0 -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    CustomTextTitle(
                        primaryText = stringResource(com.madrid.presentation.R.string.explore_more)
                    )
                }
                items(
                    count = listOfItem.itemCount,
                ) { index ->
                    val movie = listOfItem[index]
                    MovioVerticalCard(
                        description = movie?.name ?: "no description",
                        movieImage = movie?.imageUrl ?: "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
                        rate = movie?.rate?.take(3) ?: "4.5",
                        height = 180.dp,
                        onClick = {
                            navController.navigate(
                                Destinations.MovieDetailsScreen(
                                    listOfItem[index]?.id?.toInt() ?: 23453,
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}