package com.madrid.presentation.viewModel.moreViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoreViewModel(
    private val isGuestUseCase: LoginUseCase
) :
    BaseViewModel<MoreUiState, MoreEffect>(MoreUiState()),
    MoreInteractionListener {

    init {
        fetchIsGuest()
//        getProfilePicture()
//        getUsername()
//        getDarkModeState()
//        getLanguage()
//        getAppVersion()
    }

    override fun onLoginBtnClick() {
        emitNewEffect(MoreEffect.navigateToLogin)
    }

    override fun onMyRatingsBtnClick() {
        emitNewEffect(MoreEffect.navigateToMyRatings)
    }

    override fun onThemeClick() {
        TODO("Not yet implemented")
    }

    private fun getProfilePicture(): String {
        TODO("Not yet implemented")
    }

    private fun getUsername(): String {
        TODO("Not yet implemented")
    }

    private fun fetchIsGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isGuestUseCase.isGuest().collectLatest { result ->
                    updateState { it.copy(isGuest = result) }
                }
            } catch (e: Exception) {
                updateState { it.copy(isGuest = true) }
            }
        }
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