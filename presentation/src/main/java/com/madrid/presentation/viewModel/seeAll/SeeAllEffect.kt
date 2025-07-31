package com.madrid.presentation.viewModel.seeAll

sealed interface SeeAllEffect {
    data object OnNavigateBack : SeeAllEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : SeeAllEffect
}