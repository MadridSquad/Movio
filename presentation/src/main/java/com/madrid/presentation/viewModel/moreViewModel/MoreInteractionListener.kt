package com.madrid.presentation.viewModel.moreViewModel

interface MoreInteractionListener {
    fun onLoginBtnClick()
    fun onMyRatingsBtnClick()
    fun onClickTheme()
    fun onSelectTheme(themeType: ThemeType)
    fun onConfirmTheme()
    fun onClickLanguage()
    fun onConfirmLanguage()
    fun onDismissBottomSheet()
    fun onLogoutBtnClick()
}