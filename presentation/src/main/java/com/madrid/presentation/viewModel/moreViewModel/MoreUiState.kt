package com.madrid.presentation.viewModel.moreViewModel

data class MoreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null,
    val username: String? = null,
    val isThemeSheetVisible: Boolean = false,
    val isDarkModeEnabled: Boolean = false,
    val isLanguageSheetVisible: Boolean = false,
    val language: String = "English",
    val appVersion: String = "",
    val isGuest: Boolean = false,
    val isLogoutSheetVisible: Boolean = false
)