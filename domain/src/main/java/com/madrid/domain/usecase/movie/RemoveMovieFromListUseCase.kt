package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class RemoveMovieFromListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        listId: Int,
        movieId: Int,
    ): ListOperationStatus
     {
        val sessionId = authenticationRepository.getSessionId().first()
        return movieRepository.removeMovieFromList(listId,movieId , sessionId)
    }
}