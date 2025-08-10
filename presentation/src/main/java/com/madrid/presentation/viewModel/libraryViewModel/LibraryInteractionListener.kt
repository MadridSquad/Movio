package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType

interface LibraryInteractionListener {
    fun onItemClick(itemId: String)
    fun onItemWatchListClick(watchListItem : WatchListState)
    /* Click on item (watchlistItem,favoriteItem, historyItem)*/

    fun onViewAllClick(type: ViewAllType)
}