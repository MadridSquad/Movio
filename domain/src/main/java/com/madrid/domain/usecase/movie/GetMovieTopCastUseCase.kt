package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.MovieRepository

class GetMovieTopCastUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Artist> =
        movieRepository.getMovieCreditsById(movieId)
}