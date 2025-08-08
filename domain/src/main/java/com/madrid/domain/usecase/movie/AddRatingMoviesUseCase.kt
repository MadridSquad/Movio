package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class AddRatingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, rate: Double) {
        return movieRepository.addRatingMovie(movieId, rate)
    }
}