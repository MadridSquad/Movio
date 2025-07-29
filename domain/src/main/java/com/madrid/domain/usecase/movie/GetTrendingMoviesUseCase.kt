package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository

class GetTrendingMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int) = movieRepository.getTrendingMovies(page)
}