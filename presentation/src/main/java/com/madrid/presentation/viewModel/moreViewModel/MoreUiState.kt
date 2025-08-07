package com.madrid.presentation.viewModel.moreViewModel

data class MoreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null,
    val username: String = "Guest",
    val isThemeSheetVisible: Boolean = false,
    val selectedTheme: ThemeType = ThemeType.DARK,
    val currentTheme: ThemeType = ThemeType.DARK,
    val isLanguageSheetVisible: Boolean = false,
    val appVersion: String = "1.1.0v",
    val isGuest: Boolean = true,
    val isLogoutSheetVisible: Boolean = false,
)

enum class ThemeType(val value: String) {
    LIGHT("Light"),
    DARK("Dark");

    companion object {
        fun fromValue(value: String): ThemeType {
            return when (value) {
                "Light" -> LIGHT
                "Dark" -> DARK
                else -> throw IllegalArgumentException("Invalid ThemeType value: $value")
            }
        }
    }
}