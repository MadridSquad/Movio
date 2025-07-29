package com.madrid.domain.usecase.search

import com.madrid.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun invoke(
        page: Int = 1
    ) = movieRepository.getPopularMovies(page)
}