package com.madrid.presentation.screens.searchScreen.features.recentSearchLayout

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.modifier.ShimmerCard
import com.madrid.designSystem.modifier.removeWidthPaddingFromParent
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState

@SuppressLint("UnusedBoxWithConstraintsScope")
fun LazyGridScope.ForYouAndExploreScreen(
    showSearchResults: Boolean,
    isLoading: Boolean,
    isError: Boolean,
    onClickSeeAll: () -> Unit,
    parentPadding: Dp,
    forYouMovies: List<SearchScreenState.MovieUiState>,
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    onMovieClick: (SearchScreenState.MovieUiState) -> Unit = {},
) {
    if (!showSearchResults) {
        when {
            isLoading -> {
                items(12) {
                    ShimmerCard(
                        isLoading = true,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .width(150.dp)
                            .height(180.dp)
                            .padding(bottom = 12.dp)
                    )
                }
            }

            isError -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = com.madrid.presentation.R.drawable.img_no_internet),
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
                    CustomTextTitle(
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 12.dp),
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
                            .padding(bottom = 32.dp)
                            .removeWidthPaddingFromParent(parentPadding = parentPadding),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(forYouMovies) { movie ->
                            MovioVerticalCard(
                                modifier = Modifier
                                    .width(124.dp)
                                    .height(202.dp),
                                description = movie.title,
                                movieImage = movie.imageUrl,
                                rate = movie.rating,
                                imageHeight = 160.dp,
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
                items(12) {
                    ShimmerCard(
                        isLoading = true,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .width(150.dp)
                            .height(180.dp)
                            .padding(bottom = 12.dp)
                    )
                }
            }

            exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.Error -> {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
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

            exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.NotLoading && exploreMoreMovies.loadState.refresh.endOfPaginationReached -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
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
                    CustomTextTitle(
                        modifier = Modifier.padding(bottom = 12.dp),
                        primaryText = stringResource(com.madrid.presentation.R.string.explore_more)
                    )
                }
                items(
                    count = exploreMoreMovies.itemCount
                ) { index ->
                    BoxWithConstraints {
                        val cardWidth = maxWidth
                        val cardHeight = cardWidth * (180f / 158)

                        MovioVerticalCard(
                            modifier = Modifier
                                .width(cardWidth)
                                .padding(bottom = 12.dp),
                            description = exploreMoreMovies[index]!!.title,
                            movieImage = exploreMoreMovies[index]!!.imageUrl,
                            rate = exploreMoreMovies[index]!!.rating,
                            imageHeight = cardHeight,
                            onClick = {
                                onMovieClick(exploreMoreMovies[index]!!)
                            }
                        )
                    }
                }
            }
        }
    }
}