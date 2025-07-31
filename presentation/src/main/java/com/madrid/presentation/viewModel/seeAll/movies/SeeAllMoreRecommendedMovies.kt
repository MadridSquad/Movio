package com.madrid.presentation.viewModel.seeAll.movies

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase

class SeeAllMoreRecommendedMovies(
    val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    val filterMoviesByCategoryUseCase: FilterMoviesByCategoryUseCase
) : SeeAllMoviesStrategy {
    override fun getTitle(): String {
        return "More Recommended"
    }

    override suspend fun getAllMovies(page: Int): List<Movie> {
        return getRecommendedMovieUseCase(page)
    }

    override suspend fun getMoviesBasedOnCategory(categoryId: Int, page: Int): List<Movie> {
        val movies = getAllMovies(page)
        return filterMoviesByCategoryUseCase(movies,categoryId)
    }

}