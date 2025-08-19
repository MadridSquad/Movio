package com.madrid.presentation.viewModel.loginViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.UnknownException
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.R
import com.madrid.presentation.screens.loginScreen.LoginInteractionListener
import com.madrid.presentation.screens.loginScreen.component.LoginEffect
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
        val errorMessage = when {
            currentState.username.isBlank() && currentState.password.isBlank()->{
                updateState { it.copy(isUsernameValid=true,isPasswordValid=true)}
                R.string.username_and_password_are_required}
            currentState.username.isBlank() ->{
                updateState { it.copy(isUsernameValid=true)}
                R.string.username_is_required}

            currentState.password.isBlank() ->{
                updateState { it.copy(isPasswordValid=true)}


                R.string.password_is_required}
            else -> null
        }
        if (errorMessage != null) {
            updateState { it.copy(errorMessage = errorMessage ) }
            return
        }


        updateState { it.copy(isLoading = true, isGuestLoading = false, errorMessage = null) }

        viewModelScope.launch(dispatcher) {
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
                            isGuest = false
                        )
                    }
                    emitNewEffect(LoginEffect.OnLoginSuccess("Login success"))
                },
                onError = { throwable ->
                    when (throwable) {
                        is MovioException -> handleMovioException(throwable)
                        else -> {
                            updateState {
                                it.copy(
                                    isLoading = false,
                                    isGuestLoading = false,
                                    errorMessage =R.string.unknown_error
                                )
                            }
                            emitNewEffect(LoginEffect.ShowToast(R.string.unknown_error))
                        }
                    }
                }
            )
        }
    }



    override fun onLoginAsGuestClicked() {
        val currentState = state.value
        if (currentState.isLoading || currentState.isGuestLoading) return

        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isGuestLoading = true) }

            try {
                val success = loginUseCase.loginAsGuest()
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
            } catch (ex: Exception) {
                handleMovioException(
                    ex = if (ex is MovioException) ex else UnknownException(ex.message?: "Unknown error"),
                    isGuestFlow = true
                )
            }
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

    private fun handleMovioException(ex: MovioException, isGuestFlow: Boolean = false) {
        val messageResId = when (ex.message) {
            "validation.invalid_password" -> R.string.invalid_username_or_password
            "auth.invalid_credentials" -> R.string.invalid_username_or_password
            "auth.unauthorized" -> R.string.unauthorized
            "network.timeout" -> R.string.network_timeout
            "network.error" -> R.string.network_error
            "server.error" -> R.string.server_error
            "server.bad_request" -> R.string.bad_request
            "resource.not_found" -> R.string.bad_request
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
    }}