package com.madrid.presentation.screens.homeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.component.LoadingSearchCard
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.TrendingMovieCard
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrendingLayout(
    modifier: Modifier = Modifier,
    trendingViewModel: HomeViewModel = koinViewModel()
) {
    val trendingUiState by trendingViewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaces.surface)
    ) {
        CustomTextTitel(
            primaryText = stringResource(com.madrid.presentation.R.string.for_u),
            secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
            endIcon = painterResource(R.drawable.outline_alt_arrow_left),
            onSeeAllClick = {

            }
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(324.dp)
                .background(Theme.color.surfaces.surface)
        ) {
            when {
                trendingUiState.isLoading -> {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(324.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(9) {
                            LoadingSearchCard()
                        }
                    }
                }

                trendingUiState.errorMessage.isNotEmpty() -> {
                    Text(
                        text = trendingUiState.errorMessage,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(324.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(trendingUiState.trending.take(9)) { item ->
                            TrendingMovieCard(
                                imgUrl = item.posterPath,
                                movieTitle = item.title,
                                movieCategory = item.mediaType,
                                rating = item.voteAverage.toString(),
                            )
                        }
                    }
                }
            }
        }
    }

}