package com.madrid.domain.usecase.authentication

import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.UnknownException
import com.madrid.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun login(username: String, password: String): Boolean {
        return authenticationRepository.login(username, password)
    }

    suspend fun loginAsGuest(): Boolean {
        try {
            return authenticationRepository.loginAsGuest()
        } catch (e: MovioException) {
            throw e
        } catch (e: Exception) {
            throw UnknownException("Unexpected error during guest login: ${e.message ?: "Unknown"}")
        }
    }

    fun checkActiveSession(): Flow<Boolean> = authenticationRepository.isUserLoggedIn()

    fun isGuest(): Flow<Boolean> = authenticationRepository.isGuest()
}