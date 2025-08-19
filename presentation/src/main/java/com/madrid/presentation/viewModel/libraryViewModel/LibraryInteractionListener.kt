package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType

interface LibraryInteractionListener {
    fun onItemClick(itemId: String)
    fun onItemWatchListClick(watchListItem : WatchListState)
    fun onWatchListViewAllClick()
    fun onViewAllClick(type: ViewAllType)
    fun onLoginBtnClick()
    fun onAddWatchListClicked()
    fun dismissCreateListBottomSheet()
    fun onCreateWatchListButtonClicked(name : String)
    fun onDismissSnackBar()
}