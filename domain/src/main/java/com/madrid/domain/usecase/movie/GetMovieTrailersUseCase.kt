package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Trailer> =
        movieRepository.getMovieTrailersById(movieId)
}