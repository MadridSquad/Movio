package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute() {
        userRepository.logout()
    }
}