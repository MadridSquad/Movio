package com.madrid.domain.usecase.search

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun invoke(
        page: Int = 1
    ) = movieRepository.getPopularMovies(page)
}