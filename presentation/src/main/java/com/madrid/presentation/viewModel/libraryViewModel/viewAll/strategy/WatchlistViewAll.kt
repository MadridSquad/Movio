package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

class WatchlistViewAll(): ViewAllStrategy {

    override fun getTitle(): String {
        return "Watchlist"
    }

    override suspend fun getAllItems(page: Int): List<Any> {
        //TODO: Implement the logic to fetch history items
        return listOf("Movie 1", "Movie 2", "Movie 3")
    }
}