package com.madrid.domain.usecase.movie


import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.first

class GetUserRatedMovie(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<RatedMovie> {
        val sessionId: String = userRepository.getSessionId().first()
        return movieRepository.getUserMovieRate(
            sessionId = sessionId
        )
    }

    data class RatedMovie(
        val rate: Double,
        val movie: Movie
    )
}
