package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.MovioPager
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesType
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MoviesLayout(
    trendingMovies: List<MediaUiState>,
    topRatingMovies: List<MediaUiState>,
    nowPlayingMovies: List<MediaUiState>,
    upComingMovies: List<MediaUiState>,
    recommendedMovies: List<MediaUiState>,
    onScroll: (Boolean) -> Unit ={},
) {
    val navController = LocalNavController.current
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            lazyGridState.firstVisibleItemScrollOffset + (lazyGridState.firstVisibleItemIndex * 1000)
        }
            .distinctUntilChanged()
            .collect { scrollOffset ->
                val scrollDistance = scrollOffset.toFloat()
                when(scrollDistance){
                    in 0f..100f -> onScroll(false)
                    else -> onScroll(true)
                }

            }
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Box(){
                MovioPager(
                    medias = trendingMovies.take(7),
                    onClickItem = { id -> navController.navigate(Destinations.MovieDetailsScreen(id)) }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .offset(y = 390.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent , Theme.color.surfaces.surface)
                        )
                    )
            )
        }
        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.top_rating),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = topRatingMovies,
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.TOP_RATING)) },
                onMediaClick = { mediaUiState ->
                    navController.navigate(Destinations.MovieDetailsScreen(mediaUiState.id.toInt()))
                },
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }


        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.now_playing),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = nowPlayingMovies,
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.NOW_PLAYING)) },
                onMediaClick = { mediaUiState ->
                    navController.navigate(Destinations.MovieDetailsScreen(mediaUiState.id.toInt()))
                },
                headerModifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }


        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.upcoming),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = upComingMovies,
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.UPCOMING)) },
                onMediaClick = { mediaUiState ->
                    navController.navigate(Destinations.MovieDetailsScreen(mediaUiState.id.toInt()))
                },
                headerModifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }

        item(span = { GridItemSpan(2) }) {
            CustomTextTitle(
                primaryText = stringResource(com.madrid.presentation.R.string.more_recommended),
                secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
                endIcon = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { navController.navigate(Destinations.SeeAllMoviesScreen(type = SeeAllMoviesType.MORE_RECOMMENDED)) },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
            )
        }

        itemsIndexed(recommendedMovies.shuffled()) { index, media ->
            var endPaddingValue = 0
            var startPaddingValue = 0

            if (index % 2 == 0)
                startPaddingValue = 16
            else
                endPaddingValue = 16
            MovioVerticalCard(
                description = media.title,
                movieImage = media.imageUrl,
                rate = media.rating.take(3),
                height = 220.dp,
                onClick = { navController.navigate(Destinations.MovieDetailsScreen(media.id.toInt()))},
                modifier = Modifier.padding(start = startPaddingValue.dp, end = endPaddingValue.dp)
            )
        }
    }
}