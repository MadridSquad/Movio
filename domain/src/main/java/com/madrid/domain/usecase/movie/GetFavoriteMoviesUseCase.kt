package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFavoriteMoviesUseCase@Inject constructor(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): List<Movie> {
        val sessionId = authenticationRepository.getSessionId().first()
        return movieRepository.getFavoriteMovies(sessionId)
    }
}