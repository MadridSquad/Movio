package com.madrid.presentation.viewModel.seeAll.movies.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.movie.GetNowPlayingMovieUseCase
import com.madrid.presentation.R

class SeeAllNowPlayingMovies(
    val getNowPlayingMovieUseCase: GetNowPlayingMovieUseCase,
    val filterMoviesByCategoryUseCase: FilterMoviesByCategoryUseCase
) : SeeAllMoviesStrategy {
    override fun getTitle(): Int {
        return R.string.now_playing
    }

    override suspend fun getAllMovies(page: Int): List<Movie> {
        return getNowPlayingMovieUseCase(page)
    }

    override suspend fun getMoviesBasedOnCategory(categoryId: Int, page: Int): List<Movie> {
        val movies = getAllMovies(page)
        return filterMoviesByCategoryUseCase(movies, categoryId)
    }

}