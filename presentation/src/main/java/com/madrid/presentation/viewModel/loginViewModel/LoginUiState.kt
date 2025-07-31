package com.madrid.presentation.viewModel

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorState: LoginError? = null,
    val showPassword: Boolean = false,
    val isTwoFactorRequired: Boolean = false,
    val loginSuccess: Boolean = false,
    val isGuest: Boolean = false
) {
    val canLogin: Boolean
        get() = username.isNotBlank() && password.isNotBlank() && !isLoading
}

sealed class LoginError {
    data class EmptyFields(
        val usernameEmpty: Boolean,
        val passwordEmpty: Boolean
    ) : LoginError()

    object InvalidCredentials : LoginError()
    object AccountLocked : LoginError()
    object NetworkError : LoginError()
    data class GenericError(val message: String) : LoginError()
}
