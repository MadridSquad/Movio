package com.madrid.presentation.viewModel.seeAll.tvShows

sealed interface SeeAllEffect {
    data object OnNavigateBack : SeeAllEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : SeeAllEffect
}