package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

class HistoryViewAll() : ViewAllStrategy {

    override fun getTitle(): String {
        return "History"
    }

    override suspend fun getAllItems(page: Int): List<Any> {
        //TODO: Implement the logic to fetch history items
        return listOf("History Item 1", "History Item 2", "History Item 3")
    }
}