package com.madrid.presentation.screens.searchScreen.features.recentSearchLayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.component.LoadingSearchCard
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState

fun LazyGridScope.forYouAndExploreScreen(
    showSearchResults: Boolean,
    isLoading: Boolean,
    isError : Boolean,
    forYouMovies: List<SearchScreenState.MovieUiState>,
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    onMovieClick: (SearchScreenState.MovieUiState) -> Unit = {},
    onExploreClick: (LazyPagingItems<SearchScreenState.MovieUiState>) -> Unit = {},
    onClickSeeAll: () -> Unit
) {
    if (!showSearchResults) {
        when {
            isLoading -> {
                items(2) {
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

            isError -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id =  com.madrid.presentation.R.drawable.img_no_internet),
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .size(128.dp)
                                .align(CenterHorizontally),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
            forYouMovies.isNotEmpty() -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    CustomTextTitel(
                        primaryText = stringResource(com.madrid.presentation.R.string.for_u),
                        secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
                        endIcon = painterResource(R.drawable.outline_alt_arrow_left),
                        onSeeAllClick = { onClickSeeAll() }
                    )
                }
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(forYouMovies) { movie ->
                            MovioVerticalCard(
                                description = movie.title,
                                movieImage = movie.imageUrl,
                                rate = movie.rating,
                                width = 124.dp,
                                height = 160.dp,
                                onClick = { onMovieClick(movie) }
                            )
                        }
                    }
                }
            }
        }
    }


    if (!showSearchResults) {
        when {
            exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.Loading -> {
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

            exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.Error -> {
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
                            text = "Internet is not available",
                            textStyle = Theme.textStyle.title.mediumMedium16,
                            color = Theme.color.surfaces.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        MovioText(
                            text = "Please make sure you are connected to the internet and try again.",
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

            exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.NotLoading && exploreMoreMovies.loadState.refresh.endOfPaginationReached -> {
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

            exploreMoreMovies.itemCount > 0 -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    CustomTextTitel(
                        primaryText = stringResource(com.madrid.presentation.R.string.explore_more)
                    )
                }
                items(
                    count = exploreMoreMovies.itemCount,
                ) { index ->

                    MovioVerticalCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        description = exploreMoreMovies[index]!!.title,
                        movieImage = exploreMoreMovies[index]!!.imageUrl,
                        rate = exploreMoreMovies[index]!!.rating,
                        height = 222.dp,
                        gap = 12.dp,
                        onClick = {
                            onMovieClick(exploreMoreMovies[index]!!)
                        }
                    )
                }
            }
        }
    }
}