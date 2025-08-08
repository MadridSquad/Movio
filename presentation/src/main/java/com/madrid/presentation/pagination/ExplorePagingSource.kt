package com.madrid.presentation.pagination

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.search.GetExploreMoreMovieUseCase

class ExplorePagingSource(
    private val getExploreMoreMovieUseCase: GetExploreMoreMovieUseCase
) : BasePagingSource<Movie>() {

    override suspend fun loadPage(page: Int): List<Movie> {
        return getExploreMoreMovieUseCase(page)
    }
}