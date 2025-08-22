package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String = "",
        language: String = "en"
    ) {
        val sessionId = authenticationRepository.getSessionId().first()
        return movieRepository.createMovieList(sessionId, name, description, language)
    }
}