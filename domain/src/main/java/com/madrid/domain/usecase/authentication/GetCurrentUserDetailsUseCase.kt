package com.madrid.domain.usecase.authentication

import com.madrid.domain.entity.User
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class GetCurrentUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        val sessionId: String = userRepository.getSessionId().first()

        return userRepository.getCurrentUser(sessionId = sessionId)
    }
}