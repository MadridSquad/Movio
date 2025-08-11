package com.madrid.domain.usecase.authentication

import com.madrid.domain.entity.User
import com.madrid.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCurrentUserDetailsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): User? {
        val sessionId: String = authenticationRepository.getSessionId().first()
        return authenticationRepository.getCurrentUser(sessionId = sessionId)
    }
}