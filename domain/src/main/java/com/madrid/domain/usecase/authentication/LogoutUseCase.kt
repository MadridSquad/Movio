package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute() {
        authenticationRepository.logout()
    }
}