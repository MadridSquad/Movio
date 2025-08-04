package com.madrid.presentation.viewModel.moreViewModel

interface MoreInteractionListener {
    fun onLoginBtnClick()
    fun onMyRatingsBtnClick()
    fun onThemeClick()
    fun setDarkMode(isEnabled: Boolean)
    fun onLanguageBtnClick()
    fun setLanguage(language: String)
    fun onLogoutBtnClick()
    fun logout()
}