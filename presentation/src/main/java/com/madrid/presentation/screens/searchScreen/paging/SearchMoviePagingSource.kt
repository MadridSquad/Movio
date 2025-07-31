package com.madrid.presentation.screens.searchScreen.paging

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.search.GetMoviesByQueryUseCase

class SearchMoviePagingSource(
    private val query: String,
    private val getMoviesByQueryUseCase: GetMoviesByQueryUseCase,

    ) : BasePagingSource<List<Movie>>() {
    override suspend fun loadPage(page: Int): List<Movie> {
        val movies = getMoviesByQueryUseCase.invoke(query = query, page = page)
        return movies
    }
}