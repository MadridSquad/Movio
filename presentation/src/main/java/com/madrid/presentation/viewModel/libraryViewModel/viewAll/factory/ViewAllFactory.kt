package com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.FavoritesViewAll
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.HistoryViewAll
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.ViewAllStrategy
import javax.inject.Inject

class ViewAllFactory @Inject constructor(
    private val favoritesViewAll: FavoritesViewAll,
    private val historyViewAll: HistoryViewAll
) {
    fun create(type: ViewAllType): ViewAllStrategy {
        return when (type) {
            ViewAllType.FAVORITES -> favoritesViewAll
            ViewAllType.HISTORY -> historyViewAll
        }
    }
}