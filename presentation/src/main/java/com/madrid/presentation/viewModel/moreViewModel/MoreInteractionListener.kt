package com.madrid.presentation.viewModel.moreViewModel

interface MoreInteractionListener {
    fun onThemeClick(isEnabled: Boolean)
    fun setDarkMode(isEnabled: Boolean)
    fun onLanguageBtnClick()
    fun setLanguage(language: String)
    fun onLogoutBtnClick()
    fun logout()
}