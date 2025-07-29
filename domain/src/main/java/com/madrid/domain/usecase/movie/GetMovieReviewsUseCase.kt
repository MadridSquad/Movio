package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Review
import com.madrid.domain.repository.MovieRepository

class GetMovieReviewsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Review> =
        movieRepository.getMovieReviewsById(movieId)
}