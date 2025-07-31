package com.madrid.presentation.viewModel.seeAll.movies

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.movie.GetTopRatedMoviesUseCase

class SeeAllTopRatedMovies(
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val filterMoviesByCategoryUseCase: FilterMoviesByCategoryUseCase
) : SeeAllMoviesStrategy {
    override fun getTitle(): String {
        return "Top Rating"
    }

    override suspend fun getAllMovies(page: Int): List<Movie> {
        return getTopRatedMoviesUseCase(page)
    }

    override suspend fun getMoviesBasedOnCategory(categoryId: Int, page: Int): List<Movie> {
        val movies = getAllMovies(page)
        return filterMoviesByCategoryUseCase(movies, categoryId)
    }

}