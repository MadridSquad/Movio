package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.WatchList
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase

class WatchlistViewAll(
    private val getWatchListUseCase: GetWatchListsUseCase,
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "Watchlist"
    }

    override suspend fun getAllItems(page: Int): List<Movie> {
        return getWatchListUseCase()
    }
}