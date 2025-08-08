package com.madrid.presentation.pagination

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase

class ForYouPagingSource(
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase
) : BasePagingSource<Movie>() {

    override suspend fun loadPage(page: Int): List<Movie> {
        return getRecommendedMovieUseCase(page)
    }
}