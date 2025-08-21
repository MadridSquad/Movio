package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

import com.madrid.presentation.viewModel.shared.WatchListUiState


interface WatchListViewAllInteractionListener {
    fun onBackButtonClicked()
    fun onItemClick(watchList : WatchListUiState)
    fun onAddButtonClicked()
    fun dismissCreateListBottomSheet()
    fun onCreateButtonClicked(name : String)
    fun onDismissSnackBar()
    fun onTryAgainButtonClicked()
}