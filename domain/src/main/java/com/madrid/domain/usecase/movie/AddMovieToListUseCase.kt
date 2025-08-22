package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class AddMovieToListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        listId: Int,
        movieId: Int
    ) {
        val sessionId = authenticationRepository.getSessionId().first()
        return movieRepository.addMovieToList(listId, sessionId, movieId)
    }
}