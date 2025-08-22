package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import com.madrid.presentation.viewModel.shared.WatchListUiState

interface LibraryInteractionListener {
    fun onItemClick(itemId: String)
    fun onItemWatchListClick(watchListItem : WatchListUiState)
    fun onWatchListViewAllClick()
    fun onViewAllClick(type: ViewAllType)
    fun onLoginBtnClick()
    fun onAddWatchListClicked()
    fun dismissCreateListBottomSheet()
    fun onCreateWatchListButtonClicked(name : String)
    fun onDismissSnackBar()
    fun onTryAgainButtonClicked()
}