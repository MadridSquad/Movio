package com.madrid.domain.usecase.authentication


import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.UnknownException
import com.madrid.domain.exceptions.ValidationException
import com.madrid.domain.repository.UserRepository
import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.MovioException
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(username: String, password: String): Boolean {
        try {
            validateCredentials(username, password)
            val success = userRepository.login(username, password)
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
        return userRepository.loginAsGuest()
    }

    private fun validateCredentials(username: String, password: String) {
        ValidationException.apply {
            validateField("Username", username, 3)
            validateField("Password", password, 6)
        }
    }

    fun checkActiveSession(): Flow<Boolean> {
        return userRepository.isUserLoggedIn()
    }

    fun isGuest(): Flow<Boolean> {
        return userRepository.isGuest()
    }
}



