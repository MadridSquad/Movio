package com.madrid.presentation.viewModel.libraryViewModel.layout

sealed class WatchListDetailsEffect {
    object NavigateBack : WatchListDetailsEffect()
    data class NavigateToMovieDetails(val movieId: String) : WatchListDetailsEffect()
}

