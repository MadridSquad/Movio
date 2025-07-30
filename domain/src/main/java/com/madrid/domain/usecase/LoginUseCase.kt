package com.madrid.domain.usecase


import com.madrid.domain.entity.LoginResult
import com.madrid.domain.exceptions.*
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(username: String, password: String): LoginResult {
        return withContext(Dispatchers.IO) {
            try {
                validateCredentials(username, password)
                val user = userRepository.login(username, password)
                LoginResult.Success(user)
            } catch (e: Exception) {
                LoginResult.Error(e)
            }
        }
    }

    suspend fun loginAsGuest(): LoginResult {
        return withContext(Dispatchers.IO) {
            try {
                val user = userRepository.loginAsGuest()
                LoginResult.Success(user)
            } catch (e: Exception) {
                LoginResult.Error(e)
            }
        }
    }

    private fun validateCredentials(username: String, password: String) {
        when {
            username.isBlank() -> throw EmptyUsernameException()
            username.length < 3 -> throw UsernameTooShortException()
            password.isBlank() -> throw EmptyPasswordException()
            password.length < 6 -> throw WeakPasswordException()
        }
    }

    suspend fun checkActiveSession(): Boolean {
        return userRepository.getCurrentUser()?.let { user ->
            user.authToken != null && !userRepository.isTokenExpired(user.authToken)
        } ?: false
    }
}
