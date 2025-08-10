package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase

class HistoryViewAll(
    private val getHistoryUseCase: GetAllMoviesInHistoryUseCase,
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "History"
    }

    override suspend fun getAllItems(page: Int): List<Movie>  {
        return getHistoryUseCase()
    }
}