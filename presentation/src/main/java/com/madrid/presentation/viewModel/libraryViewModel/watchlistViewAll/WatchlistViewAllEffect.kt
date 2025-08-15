package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

sealed class WatchlistViewAllEffect {
    data object NavigateBack : WatchlistViewAllEffect()
    data class NavigateToDetails(val watchListId: Int, val watchListTitle: String) :
        WatchlistViewAllEffect()
}