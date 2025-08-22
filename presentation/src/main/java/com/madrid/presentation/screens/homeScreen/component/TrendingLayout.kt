package com.madrid.presentation.screens.homeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.modifier.ShimmerCard
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.TrendingMovieCard
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel

@Composable
fun TrendingLayout(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = Modifier,
    trendingViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by trendingViewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaces.surface)
    ) {
        CustomTextTitle(
            modifier = headerModifier.padding(bottom = 12.dp),
            primaryText = stringResource(com.madrid.presentation.R.string.trending),
            secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
            endIcon = painterResource(R.drawable.outline_alt_arrow_left),
            onSeeAllClick = {}
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(324.dp)
                .background(Theme.color.surfaces.surface)
        ) {
            when {
                homeState.isLoading -> {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(324.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(12) {
                            ShimmerCard(
                                isLoading = true,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .width(150.dp)
                                    .height(180.dp)
                            )
                        }
                    }
                }

                homeState.errorMessage.isNotEmpty() -> {
                    MovioText(
                        text = homeState.errorMessage,
                        modifier = Modifier.align(Alignment.Center),
                        color = Theme.color.surfaces.onSurface,
                        textStyle = Theme.textStyle.title.mediumMedium16
                    )
                }

                else -> {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(324.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(homeState.allTabUiState.trending.media) { item ->
                            TrendingMovieCard(
                                imgUrl = item.imageUrl,
                                movieTitle = item.title,
                                movieCategory = item.category.first().name,
                                rating = item.rating,
                            )
                        }
                    }
                }
            }
        }
    }

}