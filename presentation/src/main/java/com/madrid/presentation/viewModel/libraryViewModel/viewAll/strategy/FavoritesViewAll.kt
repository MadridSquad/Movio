package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

class FavoritesViewAll() : ViewAllStrategy {

    override fun getTitle(): String {
        return "Favorites"
    }

    override suspend fun getAllItems(page: Int): List<Any> {
        //TODO: Implement the logic to fetch history items
        return listOf("Favorite Movie 1", "Favorite Movie 2", "Favorite Movie 3")
    }
}