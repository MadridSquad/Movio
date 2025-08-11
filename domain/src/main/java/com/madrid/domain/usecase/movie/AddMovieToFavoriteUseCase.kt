package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(movieId: Int, isFavorite: Boolean) {
        val sessionId = authenticationRepository.getSessionId().first()
        movieRepository.addMovieToFavorite(
            movieId = movieId,
            sessionId = sessionId,
            isFavorite = isFavorite
        )
    }
}