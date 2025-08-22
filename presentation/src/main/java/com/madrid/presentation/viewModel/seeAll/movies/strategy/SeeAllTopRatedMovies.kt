package com.madrid.presentation.viewModel.seeAll.movies.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.madrid.presentation.R

class SeeAllTopRatedMovies(
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val filterMoviesByCategoryUseCase: FilterMoviesByCategoryUseCase
) : SeeAllMoviesStrategy {
    override fun getTitle(): Int {
        return R.string.top_rating
    }

    override suspend fun getAllMovies(page: Int): List<Movie> {
        return getTopRatedMoviesUseCase(page)
    }

    override suspend fun getMoviesBasedOnCategory(categoryId: Int, page: Int): List<Movie> {
        val movies = getAllMovies(page)
        return filterMoviesByCategoryUseCase(movies, categoryId)
    }

}