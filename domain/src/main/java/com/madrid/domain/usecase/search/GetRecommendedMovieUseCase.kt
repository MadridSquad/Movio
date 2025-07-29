package com.madrid.domain.usecase.search

import com.madrid.domain.repository.MovieRepository

class GetRecommendedMovieUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepository.getRecommendedMovies(page)
}