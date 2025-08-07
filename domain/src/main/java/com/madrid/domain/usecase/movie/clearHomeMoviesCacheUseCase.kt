package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class ClearHomeMoviesCacheUseCase @Inject constructor(private val moviesRepository: MovieRepository) {
    suspend operator fun invoke() {
        moviesRepository.clearHomeMoviesCache()
    }
}