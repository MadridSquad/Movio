package com.madrid.presentation.viewModel.seeAll.movies

sealed interface SeeAllEffect {
    data object OnNavigateBack : SeeAllEffect
    data class NavigateToMovieDetails(val movieId: Int) : SeeAllEffect
}