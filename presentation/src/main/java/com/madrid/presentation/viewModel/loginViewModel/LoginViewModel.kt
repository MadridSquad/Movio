package com.madrid.presentation.viewModel.loginViewModel

import LoginUiState
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.LoginResult
import com.madrid.domain.exceptions.*
import com.madrid.domain.usecase.LoginUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<LoginUiState, Nothing>(LoginUiState()) {

    fun updateUsername(username: String) {
        updateState {
            it.copy(
                username = username,
                errorState = if (username.isNotBlank()) it.errorState?.clearUsernameError() else it.errorState
            )
        }
    }

    fun updatePassword(password: String) {
        updateState {
            it.copy(
                password = password,
                errorState = if (password.isNotBlank()) it.errorState?.clearPasswordError() else it.errorState
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
            updateState { it.copy(isLoading = true, errorState = null) }

            when (val result = loginUseCase.execute(currentState.username, currentState.password)) {
                is LoginResult.Success -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            isGuest = false
                        )
                    }
                    onSuccess()
                }

                is LoginResult.Error -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorState = result.exception as? MovioException
                                ?: UnknownException(result.exception.message ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }

    fun loginAsGuest(onSuccess: () -> Unit) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorState = null) }

            when (val result = loginUseCase.loginAsGuest()) {
                is LoginResult.Success -> {
                    Log.e("TAG lol", "Succ: ")
                    updateState {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            isGuest = true
                        )
                    }
                    onSuccess()
                }

                is LoginResult.Error -> {
                    Log.e("TAG lol", "Error: ${result.exception}")
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorState = result.exception as? MovioException
                                ?: UnknownException(result.exception.message ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }
}


private fun MovioException.clearUsernameError(): MovioException? {
    return when (this) {
        is EmptyUsernameException,
        is UsernameTooShortException -> null // Clear these errors on username update
        else -> this
    }
}

private fun MovioException.clearPasswordError(): MovioException? {
    return when (this) {
        is EmptyPasswordException,
        is WeakPasswordException -> null // Clear these errors on password update
        else -> this
    }
}
