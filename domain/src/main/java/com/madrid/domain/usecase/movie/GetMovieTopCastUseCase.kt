package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieTopCastUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Artist> =
        movieRepository.getMovieCreditsById(movieId)
}