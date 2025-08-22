package com.madrid.presentation.viewModel.loginViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.R
import com.madrid.presentation.screens.loginScreen.LoginInteractionListener
import com.madrid.presentation.screens.loginScreen.component.LoginEffect
import com.madrid.presentation.viewModel.base.AuthorizationError
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.base.ErrorState
import com.madrid.presentation.viewModel.base.InvalidCredentialsError
import com.madrid.presentation.viewModel.base.NetworkError
import com.madrid.presentation.viewModel.base.ServerError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<LoginUiState, LoginEffect>(LoginUiState()), LoginInteractionListener {

    override fun onUsernameChanged(username: String) {
        updateState { it.copy(username = username, errorMessage = null, isUsernameValid = false) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password, errorMessage = null, isPasswordValid = false) }
    }

    override fun onLoginClicked() {
        val currentState = state.value
        if (currentState.isLoading || currentState.isGuestLoading) return
        updateState { it.copy(isUsernameValid = false, isPasswordValid = false) }

        val errorMessage = when {
            currentState.username.isBlank() && currentState.password.isBlank() -> {
                updateState { it.copy(isUsernameValid = true, isPasswordValid = true) }
                R.string.username_and_password_are_required
            }

            currentState.username.isBlank() -> {
                updateState { it.copy(isUsernameValid = true) }
                R.string.username_is_required
            }

            currentState.password.isBlank() -> {
                updateState { it.copy(isPasswordValid = true) }


                R.string.password_is_required
            }

            else -> null
        }

        if (errorMessage != null) {
            updateState { it.copy(errorMessage = errorMessage) }
            return
        }


        updateState { it.copy(isLoading = true, isGuestLoading = false, errorMessage = null) }

        tryToExecute(
            function = {
                loginUseCase.login(
                    username = currentState.username,
                    password = currentState.password
                )
            },
            onSuccess = { sessionId ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isGuestLoading = false,
                        loginSuccess = true,
                        isGuest = false,
                        isUsernameValid = false,
                        isPasswordValid = false
                    )
                }
                emitNewEffect(LoginEffect.OnLoginSuccess("Login success"))
            },
            onError = { throwable ->
                handleMovioException(throwable)
            }
        )
    }


    override fun onLoginAsGuestClicked() {
        val currentState = state.value
        if (currentState.isLoading || currentState.isGuestLoading) return

        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isGuestLoading = true) }

            tryToExecute(
                function = {
                    loginUseCase.loginAsGuest()
                },
                onSuccess = { success ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            isGuestLoading = false,
                            isGuest = success,
                            loginSuccess = success
                        )
                    }
                    if (success) {
                        emitNewEffect(LoginEffect.OnLoginSuccess("Guest login success"))
                    }
                },
                onError = { ex ->
                    handleMovioException(
                        ex = ex,
                        isGuestFlow = true
                    )
                }
            )
        }
    }

    override fun onForgotPasswordClicked() {
        emitNewEffect(LoginEffect.WebView("https://www.themoviedb.org/reset-password"))
    }

    override fun onSignUpClicked() {
        emitNewEffect(LoginEffect.WebView("https://www.themoviedb.org/signup"))
    }

    override fun onShowPasswordToggled() {
        updateState { it.copy(showPassword = !it.showPassword) }
    }

    private fun handleMovioException(ex: ErrorState, isGuestFlow: Boolean = false) {
        val messageResId = when (ex) {
            is InvalidCredentialsError -> {

                updateState { it.copy(isUsernameValid = true, isPasswordValid = true) }
                R.string.invalid_username_or_password
            }
            is InvalidCredentialsError -> R.string.invalid_username_or_password
            is AuthorizationError -> R.string.unauthorized
            is NetworkError -> R.string.network_error
            is ServerError -> R.string.server_error
            else -> R.string.unknown_error
        }

        updateState {
            it.copy(
                isLoading = false,
                isGuestLoading = false,
                errorMessage = if (!isGuestFlow) messageResId else null
            )
        }

        if (isGuestFlow || messageResId == R.string.network_error || messageResId == R.string.server_error) {
            emitNewEffect(LoginEffect.ShowToast(messageResId))
        }
    }
}