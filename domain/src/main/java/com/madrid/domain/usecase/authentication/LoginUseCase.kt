package com.madrid.domain.usecase.authentication

import com.madrid.domain.exceptions.EmptyPasswordException
import com.madrid.domain.exceptions.EmptyUsernameException
import com.madrid.domain.exceptions.UsernameTooShortException
import com.madrid.domain.exceptions.WeakPasswordException
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(username: String, password: String): Boolean {
        validateCredentials(username, password)
        return userRepository.login(username, password)
    }

    suspend fun loginAsGuest(): Boolean {
        return userRepository.loginAsGuest()
    }

    private fun validateCredentials(username: String, password: String) {
        when {
            username.isBlank() -> throw EmptyUsernameException()
            username.length < 3 -> throw UsernameTooShortException()
            password.isBlank() -> throw EmptyPasswordException()
            password.length < 6 -> throw WeakPasswordException()
        }
    }

    fun checkActiveSession(): Flow<Boolean> {
        return userRepository.isUserLoggedIn()
    }
}