package com.madrid.domain.usecase.movie


import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository

class GetUserRatedMovie(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<RatedMovie> =
        movieRepository.getUserMovieRate()

    data class RatedMovie(
        val rate: Double,
        val movie: Movie
    )
}
