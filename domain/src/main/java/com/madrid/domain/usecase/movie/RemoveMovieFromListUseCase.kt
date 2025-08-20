package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveMovieFromListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        listId: Int,
        movieId: Int
    ): ListOperationStatus {
        return try {
            val sessionId = authenticationRepository.getSessionId().first()

            if (sessionId.isEmpty()) {
                return ListOperationStatus(
                    success = false,
                    message = "User not authenticated"
                )
            }

            movieRepository.removeMovieFromList(listId, movieId, sessionId)
        } catch (e: Exception) {
            ListOperationStatus(
                success = false,
                message = e.message ?: "Failed to remove movie from list"
            )
        }
    }
}