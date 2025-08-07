package com.madrid.presentation.pagination

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.search.GetMoviesByQueryUseCase

class SearchMoviePagingSource(
    private val query: String,
    private val getMoviesByQueryUseCase: GetMoviesByQueryUseCase,

    ) : BasePagingSource<Movie>() {
    override suspend fun loadPage(page: Int): List<Movie> {
        val movies = getMoviesByQueryUseCase.invoke(query = query, page = page)
        return movies
    }
}