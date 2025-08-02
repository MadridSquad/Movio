package com.madrid.presentation.viewModel.moreViewModel

import com.madrid.presentation.viewModel.base.BaseViewModel

class MoreViewModel() :
    BaseViewModel<MoreUiState, MoreEffect>(MoreUiState()),
    MoreInteractionListener {

    init {
//        updateState {
//            MoreUiState(
//                isLoading = false,
//                errorMessage = null,
//                profilePictureUrl = getProfilePicture(),
//                username = getUsername(),
//                isThemeSheetVisible = false,
//                isDarkModeEnabled = getDarkModeState(),
//                isLanguageSheetVisible = false,
//                language = getLanguage(),
//                appVersion = getAppVersion(),
//                isGuest = isGuest(),
//                isLogoutSheetVisible = false
//            )
//        }
    }

    override fun onThemeClick(isEnabled: Boolean) {
        TODO("Not yet implemented")
    }

    private fun getProfilePicture(): String {
        TODO("Not yet implemented")
    }

    private fun getUsername(): String {
        TODO("Not yet implemented")
    }

    private fun isGuest(): Boolean {
        TODO("Not yet implemented")
    }
    override fun setDarkMode(isEnabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onLanguageBtnClick() {
        TODO("Not yet implemented")
    }

    private fun getDarkModeState(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setLanguage(language: String) {
        TODO("Not yet implemented")
    }

    override fun onLogoutBtnClick() {
        TODO("Not yet implemented")
    }

    private fun getLanguage(): String {
        TODO("Not yet implemented")
    }

    private fun getAppVersion(): String {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

}