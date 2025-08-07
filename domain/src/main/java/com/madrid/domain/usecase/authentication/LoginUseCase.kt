package com.madrid.domain.usecase.authentication


import com.madrid.domain.exceptions.GuestLoginException
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

                else -> UnknownException("error during login: ${e.message ?: "Unknown error"}")
            }

        }
    }

    suspend fun loginAsGuest(): Boolean {

        try {
            val successisGuest = authenticationRepository.loginAsGuest()
            if (!successisGuest) throw GuestLoginException()
            return true
        } catch (e: MovioException) {
            throw e
        } catch (e: Exception) {
            throw UnknownException("e $e.message ?:")

        }


    }


    private fun validateCredentials(username: String, password: String) {
        ValidationException.apply {
            validateField("Username", username)
            validateField("Password", password)
        }
    }

    fun checkActiveSession(): Flow<Boolean> {
        return authenticationRepository.isUserLoggedIn()
    }

    fun isGuest(): Flow<Boolean> {
        return authenticationRepository.isGuest()
    }
}



