package com.madrid.presentation.viewModel.seeAll.movies.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.presentation.R

class SeeAllMoreRecommendedMovies(
    val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    val filterMoviesByCategoryUseCase: FilterMoviesByCategoryUseCase
) : SeeAllMoviesStrategy {
    override fun getTitle(): Int {
        return R.string.more_recommended
    }

    override suspend fun getAllMovies(page: Int): List<Movie> {
        return getRecommendedMovieUseCase(page)
    }

    override suspend fun getMoviesBasedOnCategory(categoryId: Int, page: Int): List<Movie> {
        val movies = getAllMovies(page)
        return filterMoviesByCategoryUseCase(movies,categoryId)
    }

}