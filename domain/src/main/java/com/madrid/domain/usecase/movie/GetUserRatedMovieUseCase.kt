package com.madrid.domain.usecase.movie


import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetUserRatedMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): List<RatedMovie> {
        val sessionId: String = authenticationRepository.getSessionId().first()
        return movieRepository.getUserMovieRate(
            sessionId = sessionId
        )
    }

    data class RatedMovie(
        val rate: Double,
        val movie: Movie
    )
}
