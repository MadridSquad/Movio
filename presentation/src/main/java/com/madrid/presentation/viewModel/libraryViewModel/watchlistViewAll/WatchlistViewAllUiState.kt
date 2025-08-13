package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

import com.madrid.presentation.viewModel.libraryViewModel.WatchListState

data class WatchlistViewAllUiState(
    val watchLists: List<WatchListState> = emptyList(),
    val showCreateListBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
