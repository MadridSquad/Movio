package com.madrid.presentation.viewModel.loginViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.LoginResult
import com.madrid.domain.exceptions.AccountLockedException
import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.viewModel.LoginError
import com.madrid.presentation.viewModel.LoginUiState
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
                true -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            isGuest = false
                        )
                    }
                    onSuccess()
                }

                false -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            // TODO errorState =
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
                true -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            isGuest = true
                        )
                    }
                    onSuccess()
                }

                false -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            // TODO errorState = result.toLoginError()
                        )
                    }
                }
            }
        }
    }

    private fun LoginResult.Error.toLoginError(): LoginError {
        return when (val ex = this.exception) {
            is InvalidCredentialsException -> LoginError.InvalidCredentials
            is AccountLockedException -> LoginError.AccountLocked
            is NetworkException -> LoginError.NetworkError
            else -> LoginError.GenericError(ex.message ?: "Unknown error")
        }
    }


    private fun LoginError.clearUsernameError(): LoginError {
        return when (this) {
            is LoginError.EmptyFields -> this.copy(usernameEmpty = false)
            else -> this
        }
    }

    private fun LoginError.clearPasswordError(): LoginError {
        return when (this) {
            is LoginError.EmptyFields -> this.copy(passwordEmpty = false)
            else -> this
        }
    }
}
