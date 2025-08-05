package com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.FavoritesViewAll
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.HistoryViewAll
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.ViewAllStrategy
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.WatchlistViewAll
import javax.inject.Inject

class ViewAllFactory @Inject constructor(
    // Use cases for fetching
) {
    fun create(type: ViewAllType): ViewAllStrategy {
        return when (type) {
            ViewAllType.WATCHLIST -> WatchlistViewAll()
            ViewAllType.FAVORITES -> FavoritesViewAll()
            ViewAllType.HISTORY -> HistoryViewAll()
        }
    }
}