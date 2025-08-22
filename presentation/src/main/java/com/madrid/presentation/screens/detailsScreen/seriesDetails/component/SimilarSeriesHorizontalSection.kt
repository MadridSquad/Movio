package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeries
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarSeriesSection
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsUiState

@Composable
fun SimilarSeriesHorizontalSection(
    modifier: Modifier = Modifier,
    uiState: SeriesDetailsUiState,
    onSeeAllClick: () -> Unit,
    onSimilarSeriesCardClick: (Int) -> Unit,
) {
    if (uiState.similarSeries.isNotEmpty()) {
        SimilarSeriesSection(
            modifier = modifier,
            similarSeries = uiState.similarSeries.map { series ->
                SimilarSeries(
                    id = series.id,
                    title = series.name,
                    imageUrl = series.imageUrl,
                    rating = series.rate.take(3).toDoubleOrNull() ?: 0.0
                )
            },
            onSeeAllClick = { onSeeAllClick() },
            onSeriesClick = { series -> onSimilarSeriesCardClick(series.id) },
        )
    }
}