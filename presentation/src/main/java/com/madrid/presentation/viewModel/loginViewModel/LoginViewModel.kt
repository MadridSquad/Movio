package com.madrid.presentation.viewModel.loginViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.exceptions.AccountLockedException
import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.ValidationException
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<LoginUiState, Nothing>(LoginUiState()) {

    fun updateUsername(username: String) {
        updateState {
            it.copy(
                username = username,
                errorMessage = if (username.isNotBlank()) null else it.errorMessage
            )
        }
    }

    fun updatePassword(password: String) {
        updateState {
            it.copy(
                password = password,
                errorMessage = if (password.isNotBlank()) null else it.errorMessage
            )
        }
    }

    fun toggleShowPassword() {
        updateState { it.copy(showPassword = !it.showPassword) }
    }

    fun login(onSuccess: () -> Unit) {
        val currentState = state.value
        if (!currentState.canLogin) return

        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null) }

            try {
                val success = loginUseCase.execute(currentState.username, currentState.password)
                if (success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            isGuest = false
                        )
                    }
                    onSuccess()
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(ex)
                    )
                }
            }
        }
    }

    fun loginAsGuest(onSuccess: () -> Unit) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null) }

            try {
                val success = loginUseCase.loginAsGuest()
                if (success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            isGuest = true
                        )
                    }
                    onSuccess()
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(ex)
                    )
                }
            }
        }
    }

    private fun getErrorMessage(exception: MovioException): String {
        return when (exception) {
            is InvalidCredentialsException -> "Invalid username or password"
            is AccountLockedException -> "Account locked. Contact support."
            is NetworkException -> "Network error. Try again."
            is ValidationException.EmptyField.Username -> "Username cannot be empty"
            is ValidationException.EmptyField.Password -> "Password cannot be empty"
            is ValidationException.MinimumLength.Username -> "Username must be at least 3 characters"
            is ValidationException.MinimumLength.Password -> "Password must be at least 6 characters"
            else -> exception.message ?: "Unknown error occurred"
        }
    }
}
