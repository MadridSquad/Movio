package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(mediaId: Int) =
        runBlocking {
            val sessionId = authenticationRepository.getSessionId()
            movieRepository.addMovieToFavorite(
                mediaId = mediaId,
                sessionId = sessionId.toString()
            )
        }
}