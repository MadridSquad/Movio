package com.madrid.presentation.pagination

import com.madrid.domain.entity.Movie


class SeeAllMoviesPagingSource(
    private val getAllMovie: suspend (Int) -> List<Movie>
) : BasePagingSource<Movie>() {

    override suspend fun loadPage(page: Int): List<Movie> {
        return getAllMovie(page)
    }
}

class SeeAllMoviesWithGenrePagingSource(
    private val genreId: Int,
    private val getAllMovie: suspend (genre: Int, Int) -> List<Movie>
) : BasePagingSource<Movie>() {

    override suspend fun loadPage(page: Int): List<Movie> {
        return getAllMovie(genreId, page)
    }
}