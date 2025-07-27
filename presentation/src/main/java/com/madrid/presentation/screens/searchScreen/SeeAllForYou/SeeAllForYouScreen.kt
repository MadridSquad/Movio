package com.madrid.presentation.screens.searchScreen.SeeAllForYou

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.R
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.LoadingSearchCard
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeeAllForYouScreen(
    viewModel: SeeAllForYouViewModel = koinViewModel(),
) {
    val navController = LocalNavController.current
    val uiState by viewModel.state.collectAsState()

    SeeAllForYouScreenContent(
        onClickBackIcon = {
            navController.navigate(Destinations.SearchScreen)
        },
        exploreMoreMovies = uiState.forYouMovies.collectAsLazyPagingItems(),
        onMovieClick = { movieId ->
            navController.navigate(Destinations.MovieDetailsScreen(movieId))
        }
    )
}

@Composable
private fun SeeAllForYouScreenContent(
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    onClickBackIcon: () -> Unit,
    onExploreClick: (LazyPagingItems<SearchScreenState.MovieUiState>) -> Unit = {},
    onMovieClick: (Int) -> Unit = {}
) {

    val isLoading = exploreMoreMovies.loadState.refresh is LoadState.Loading
    val isError = exploreMoreMovies.loadState.refresh is LoadState.Error
    val isEmpty =
        exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.NotLoading && exploreMoreMovies.loadState.refresh.endOfPaginationReached


    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
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
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioIcon(
                    modifier = Modifier.clickable { onClickBackIcon() },
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = null,
                    tint = Theme.color.surfaces.onSurface
                )
                Spacer(Modifier.width(8.dp))
                MovioText(
                    text = stringResource(com.madrid.presentation.R.string.for_u),
                    color = Theme.color.surfaces.onSurface,
                    textStyle = Theme.textStyle.headline.largeBold16,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }
            Spacer(Modifier.height(16.dp))

        }

        when {
            isLoading -> {
                items(9) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingSearchCard()
                    }
                }
            }

            isError -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptySearchLayout(
                            title = stringResource(com.madrid.presentation.R.string.internet_is_not_available),
                            description = "Please make sure you are connected to the internet and try again.",
                            image = com.madrid.presentation.R.drawable.img_no_internet
                        )
                    }
                }
            }

            isEmpty -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptySearchLayout(
                            title = stringResource(com.madrid.presentation.R.string.no_results_found),
                            description = stringResource(com.madrid.presentation.R.string.we_couldn_t_find_anything_matching_your_search_try_checking_the_spelling_or_explore_something_else),
                            image = com.madrid.presentation.R.drawable.img_no_sesrch_found
                        )
                    }
                }
            }

            else -> {
                items(
                    count = exploreMoreMovies.itemCount,
                ) { index ->
                    MovioVerticalCard(
                        modifier = Modifier.fillMaxWidth(fraction = 0.50f),
                        description = exploreMoreMovies[index]!!.title,
                        movieImage = exploreMoreMovies[index]!!.imageUrl,
                        rate = exploreMoreMovies[index]!!.rating,
                        width = 101.dp,
                        height = 136.dp,
                        onClick = { onMovieClick(exploreMoreMovies[index]!!.id.toInt()) }
                    )
                }
            }
        }
    }
}