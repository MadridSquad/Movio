package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(mediaId: Int) {
        val sessionId = authenticationRepository.getSessionId().first()
        movieRepository.addMovieToFavorite(
            mediaId = mediaId,
            sessionId = sessionId.toString()
        )
    }
}