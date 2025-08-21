package com.madrid.presentation.screens.searchScreen.features.recentSearchLayout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.HeaderSectionBar
import com.madrid.designSystem.component.LoadingSearchCard
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.screens.searchScreen.utils.FilterPagesItem
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FilterSearchScreen(
    typeOfFilterSearch: FilterPagesItem,
    onChangeTypeFilterSearch: () -> Unit,
    selectedTabIndex: Int,
    onChangeSelectedTabIndex: (Int) -> Unit,
    topRated: LazyPagingItems<SearchScreenState.MovieUiState>,
    movies: LazyPagingItems<SearchScreenState.MovieUiState>,
    series: LazyPagingItems<SearchScreenState.SeriesUiState>,
    artist: LazyPagingItems<SearchScreenState.ArtistUiState>,
    onSeriesClick: (Int) -> Unit = {},
    onMovieClick: (Int) -> Unit,
    onTopResultClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
) {
    HeaderSectionBar(
        modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
        tabs = listOf(
            stringResource(R.string.top_results),
            stringResource(R.string.Movies),
            stringResource(R.string.Series),
            stringResource(R.string.Artists),
        ),
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            onChangeSelectedTabIndex(index)
            onChangeTypeFilterSearch()
        },
    )

    when (typeOfFilterSearch) {
        FilterPagesItem.TOP_RATED -> {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 101.33.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    SearchResultMessage(
                        items = topRated.itemCount.toString()
                    )
                }

                when {
                    topRated.itemCount == 0 && topRated.loadState.refresh is LoadState.Loading -> {
                        items(9) {
                            LoadingSearchCard()
                        }
                    }

                    topRated.itemCount == 0 && topRated.loadState.refresh is LoadState.Error -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.no_results_found),
                                    description = stringResource(R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                                    image = Theme.drawables.notFoundLayoutId
                                )
                            }
                        }
                    }

                    topRated.itemCount == 0 &&
                            topRated.loadState.refresh is LoadState.NotLoading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.no_results_found),
                                    description = stringResource(R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                                    image = Theme.drawables.notFoundLayoutId
                                )
                            }
                        }
                    }

                    topRated.itemCount > 0 -> {
                        items(count = topRated.itemCount) { index ->
                            BoxWithConstraints {
                                val cardWidth = maxWidth
                                val cardHeight = cardWidth * (136f / 101.33f)

                                MovioVerticalCard(
                                    modifier = Modifier.width(cardWidth),
                                    description = topRated[index]!!.title,
                                    movieImage = topRated[index]!!.imageUrl,
                                    rate = topRated[index]!!.rating,
                                    imageHeight = cardHeight,
                                    onClick = { onTopResultClick(topRated[index]!!.id.toInt()) }
                                )
                            }
                        }
                    }
                }
            }
        }

        FilterPagesItem.MOVIES -> {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 101.33.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    SearchResultMessage(
                        items = movies.itemCount.toString()
                    )
                }

                when {
                    movies.itemCount == 0 && movies.loadState.refresh is LoadState.Loading -> {
                        items(9) {
                            LoadingSearchCard()
                        }
                    }

                    movies.itemCount == 0 && movies.loadState.refresh is LoadState.Error -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.no_results_found),
                                    description = stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                                    image = Theme.drawables.noInternetId
                                )
                            }
                        }
                    }

                    movies.itemCount == 0 &&
                            movies.loadState.refresh is LoadState.NotLoading -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }

                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.no_results_found),
                                    description = stringResource(R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                                    image = Theme.drawables.notFoundLayoutId
                                )
                            }
                        }
                    }

                    movies.itemCount > 0 -> {
                        items(count = movies.itemCount) { index ->
                            BoxWithConstraints {
                                val cardWidth = maxWidth
                                val cardHeight = cardWidth * (136f / 101.33f)

                                MovioVerticalCard(
                                    modifier = Modifier.width(cardWidth),
                                    description = movies[index]!!.title,
                                    movieImage = movies[index]!!.imageUrl,
                                    rate = movies[index]!!.rating,
                                    imageHeight = cardHeight,
                                    onClick = { onMovieClick(movies[index]!!.id.toInt()) }
                                )
                            }
                        }
                    }
                }
            }
        }

        FilterPagesItem.SERIES -> {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 101.33.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    SearchResultMessage(
                        items = series.itemCount.toString()
                    )
                }

                when {
                    series.itemCount == 0 && series.loadState.refresh is LoadState.Loading -> {
                        items(9) {
                            LoadingSearchCard()
                        }
                    }

                    series.itemCount == 0 && series.loadState.refresh is LoadState.Error -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.internet_is_not_available),
                                    description =
                                        stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                                    image = Theme.drawables.noInternetId
                                )
                            }
                        }
                    }

                    series.itemCount == 0 &&
                            series.loadState.refresh is LoadState.NotLoading -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.no_results_found),
                                    description = stringResource(R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                                    image = Theme.drawables.notFoundLayoutId
                                )
                            }
                        }
                    }

                    series.itemCount > 0 -> {
                        items(count = series.itemCount) { index ->
                            BoxWithConstraints {
                                val cardWidth = maxWidth
                                val cardHeight = cardWidth * (136f / 101.33f)

                                MovioVerticalCard(
                                    modifier = Modifier.width(cardWidth),
                                    description = series[index]!!.title,
                                    movieImage = series[index]!!.imageUrl,
                                    rate = series[index]!!.rating,
                                    imageHeight = cardHeight,
                                    onClick = { onSeriesClick(series[index]!!.id.toInt()) }
                                )
                            }
                        }
                    }
                }
            }

        }

        FilterPagesItem.ARTISTS -> {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 101.33.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    SearchResultMessage(
                        items = artist.itemCount.toString()
                    )
                }

                when {
                    artist.itemCount == 0 && artist.loadState.refresh is LoadState.Loading -> {
                        items(9) {
                            LoadingSearchCard()
                        }
                    }


                    artist.itemCount == 0 && artist.loadState.refresh is LoadState.Error -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.internet_is_not_available),
                                    description = "Please make sure you are connected to the internet and try again.",
                                    image = Theme.drawables.noInternetId
                                )
                            }
                        }
                    }

                    artist.itemCount == 0 &&
                            artist.loadState.refresh is LoadState.NotLoading -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                EmptySearchLayout(
                                    title = stringResource(R.string.no_results_found),
                                    description = stringResource(R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                                    image = Theme.drawables.notFoundLayoutId
                                )
                            }
                        }
                    }

                    artist.itemCount > 0 -> {
                        items(count = artist.itemCount) { index ->
                            BoxWithConstraints {
                                val cardWidth = maxWidth
                                val circleImageSize = cardWidth * (88f / 101.33f)

                                MovioArtistsCard(
                                    modifier = Modifier.width(cardWidth),
                                    artistsName = artist[index]!!.name,
                                    paddingBetweenImageAndText = 8.dp,
                                    imageUrl = artist[index]!!.imageUrl,
                                    onClick = { onActorClick(artist[index]!!.id.toInt()) },
                                    circleImageSize = circleImageSize
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}