package com.madrid.domain.usecase.authentication

import com.madrid.domain.entity.User
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class GetCurrentUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        val accountId: Int = 22132081
        // This should be replaced with the actual logic to retrieve the account ID
        val sessionId: String = userRepository.getSessionId().first()

        return userRepository.getCurrentUser(accountId, sessionId)
    }
}