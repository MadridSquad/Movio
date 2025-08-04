package com.madrid.presentation.viewModel.logoutViewModel

data class LogoutUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val logoutSuccess: Boolean = false,
    val isGuest: Boolean = false
)