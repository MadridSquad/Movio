package com.madrid.presentation.viewModel.loginViewModel

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val errorMessage: String? = null,
    val loginSuccess: Boolean = false,
    val isGuest: Boolean = false,
    val isLoading: Boolean = false
) {
    val canLogin: Boolean
        get() = username.isNotBlank() && password.isNotBlank()
}