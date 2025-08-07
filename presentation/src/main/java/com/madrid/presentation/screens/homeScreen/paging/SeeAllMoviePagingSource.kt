package com.madrid.presentation.screens.homeScreen.paging

import com.madrid.domain.entity.Movie
import com.madrid.presentation.screens.searchScreen.paging.BasePagingSource

class SeeAllMoviesPagingSource(
    private val getAllMovie: suspend (Int)-> List<Movie>
) : BasePagingSource<List<Movie>>() {

    override suspend fun loadPage(page: Int): List<Movie> {
        return getAllMovie(page)
    }
}

class SeeAllMoviesWithGenrePagingSource(
    private val genreId : Int,
    private val getAllMovie: suspend (genre :Int,Int)-> List<Movie>
) : BasePagingSource<List<Movie>>() {

    override suspend fun loadPage(page: Int): List<Movie> {
        return getAllMovie(genreId,page)
    }
}