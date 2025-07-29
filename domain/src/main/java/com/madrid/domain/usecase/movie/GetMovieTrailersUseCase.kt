package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository

class GetMovieTrailersUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Trailer> =
        movieRepository.getMovieTrailersById(movieId)
}