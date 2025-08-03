package com.madrid.presentation.viewModel.moreViewModel

data class MoreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null,
    val username: String = "Guest",
    val isThemeSheetVisible: Boolean = false,
    val isDarkModeEnabled: Boolean = false,
    val isLanguageSheetVisible: Boolean = false,
    val language: String = "English",
    val appVersion: String = "1.1.0v",
    val isGuest: Boolean = true,
    val isLogoutSheetVisible: Boolean = false
)