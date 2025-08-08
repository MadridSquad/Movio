package com.madrid.presentation.viewModel.libraryViewModel

interface LibraryInteractionListener {
    fun onItemClick(itemId: String)
    fun onItemWatchListClick(watchListItem : WatchListState)
    /* Click on item (watchlistItem,favoriteItem, historyItem)*/

    fun onViewAllClick(type: String)
}