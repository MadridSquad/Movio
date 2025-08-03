package com.madrid.presentation.viewModel.moreViewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MoreViewModel @Inject constructor(
    private val isGuestUseCase: LoginUseCase,
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase
) :
    BaseViewModel<MoreUiState, MoreEffect>(MoreUiState()),
    MoreInteractionListener {

    init {
        fetchIsGuest()
        fetchCurrentUserDetails()
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

    private fun fetchCurrentUserDetails() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("Tag fetchCurrentUserDetails"," user is:   ")
                val user = getCurrentUserDetailsUseCase()
                Log.d("Tag fetchCurrentUserDetails"," user is:   $user")
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        username = user?.username ?: "",
                        profilePictureUrl = user?.profilePicUrl ?: ""
                    )
                }
            } catch (e: Exception) {
                Log.e("MoreViewModel", "Error fetching current user details", e)
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An error occurred",
                        username = "Guest",
                        profilePictureUrl = null // Add an error for profile picture
                    )
                }
            }
        }
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