package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SetMovieFavoriteStatusUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(movieId: Int, isFavorite: Boolean) {
        val sessionId = authenticationRepository.getSessionId().first()
        movieRepository.setMovieFavoriteStatus(
            movieId = movieId,
            sessionId = sessionId,
            isFavorite = isFavorite
        )
    }
}