package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeries
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeriesSection
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState

@Composable
fun SimilarSeriesHorizontalSection(
    navController: NavHostController,
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener
) {
    if (uiState.similarSeries.isNotEmpty()) {
        SimilarSeriesSection(
            similarSeries = uiState.similarSeries.map { series ->
                SimilarSeries(
                    id = series.id,
                    title = series.name,
                    imageUrl = series.imageUrl,
                    rating = series.rate.take(3).toDoubleOrNull() ?: 0.0
                )
            },
            onSeeAllClick = {
                navController.navigate(
                    Destinations.SimilarMediaScreen(
                        mediaId = uiState.seriesId,
                        isMovie = false
                    )
                )
            },
            onSeriesClick = { series ->
                interactionListener.onSimilarSeriesCardClick(series.id)
            },
            modifier = Modifier.navigationBarsPadding()
        )
    }
}