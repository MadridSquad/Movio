package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase

class FavoritesViewAll(
    private val getFavoriteUseCase: GetFavoriteMoviesUseCase,
    ) : ViewAllStrategy {

    override fun getTitle(): String {
        return "Favorites"
    }

    override suspend fun getAllItems(page: Int): List<Movie> {
        return getFavoriteUseCase()
    }
}