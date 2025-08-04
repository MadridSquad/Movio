package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.SortType
import com.madrid.domain.repository.MovieRepository

class addRatingMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, rate: Double) {
        return movieRepository.addRatingMovie(movieId, rate)
    }
}