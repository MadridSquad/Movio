package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

import com.madrid.presentation.viewModel.libraryViewModel.WatchListState

interface WatchListViewAllInteractionListener {
    fun onBackButtonClicked()
    fun onItemClick(watchList : WatchListState)
    fun onAddButtonClicked()
    fun dismissCreateListBottomSheet()
    fun onCreateButtonClicked(name : String)
    fun onDismissSnackBar()
    fun onTryAgainButtonClicked()
}