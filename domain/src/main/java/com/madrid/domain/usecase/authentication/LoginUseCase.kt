package com.madrid.domain.usecase.authentication

import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.UnknownException
import com.madrid.domain.exceptions.ValidationException
import com.madrid.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun execute(username: String, password: String): Boolean {
        try {
            validateCredentials(username, password)
            val success = authenticationRepository.login(username, password)
            if (!success) throw InvalidCredentialsException()
            return true
        } catch (e: MovioException) {
            throw e
        } catch (e: Exception) {
            throw when (e) {
                else -> UnknownException("Wrong username or password")
            }
        }
    }

    suspend fun loginAsGuest(): Boolean {
        return authenticationRepository.loginAsGuest()
    }

    private fun validateCredentials(username: String, password: String) {
        ValidationException.apply {
            validateField("Username", username, 3)
            validateField("Password", password, 6)
        }
    }

    fun checkActiveSession(): Flow<Boolean> {
        return authenticationRepository.isUserLoggedIn()
    }

    fun isGuest(): Flow<Boolean> {
        return authenticationRepository.isGuest()
    }
}



