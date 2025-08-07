package com.madrid.presentation.viewModel.loginViewModel


import androidx.lifecycle.viewModelScope
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.ValidationException
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<LoginUiState, Nothing>(LoginUiState()) {
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

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
    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun dismissToast() {
        _toastMessage.value = null
    }



    fun login(onSuccess: () -> Unit) {
        val currentState = state.value

        try {
            ValidationException.validateField("Username", currentState.username)
            ValidationException.validateField("Password", currentState.password)
        } catch (ex: ValidationException) {
            updateState { it.copy(errorMessage = ex.message) }
            return
        }

        if (currentState.isLoading || currentState.isGuestLoading) return

        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, isGuestLoading = false, errorMessage = null) }
            try {
                val success = loginUseCase.execute(currentState.username, currentState.password)
                if (success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isGuestLoading = false,
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
                        isGuestLoading = false,
                        errorMessage = ex.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun loginAsGuest(onSuccess: () -> Unit) {
        val currentState = state.value
        if (currentState.isLoading || currentState.isGuestLoading) return

        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = false, isGuestLoading = true, errorMessage = null) }
            try {
                val success = loginUseCase.loginAsGuest()
                if (success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isGuestLoading = false,
                            loginSuccess = true,
                            isGuest = true
                        )
                    }
                    onSuccess()
                }
            } catch (ex: MovioException) {
                showToast(ex.message ?: "Something went wrong")
                updateState {
                    it.copy(
                        isLoading = false,
                        isGuestLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }
}



