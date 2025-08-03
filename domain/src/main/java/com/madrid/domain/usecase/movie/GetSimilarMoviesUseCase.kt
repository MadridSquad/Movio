package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Movie> =
        movieRepository.getSimilarMoviesById(movieId)
}