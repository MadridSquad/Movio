package com.madrid.presentation.viewModel.moreViewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.preferences.GetAppThemeUseCase
import com.madrid.domain.usecase.preferences.GetLanguageUseCase
import com.madrid.domain.usecase.preferences.SetAppThemeUseCase
import com.madrid.domain.usecase.preferences.SetLanguageUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val isGuestUseCase: LoginUseCase,
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
) :
    BaseViewModel<MoreUiState, MoreEffect>(MoreUiState()),
    MoreInteractionListener {

    init {
        fetchIsGuest()
        fetchCurrentUserDetails()
//        getAppVersion()
    }

    override fun onLoginBtnClick() {
        emitNewEffect(MoreEffect.navigateToLogin)
    }

    override fun onMyRatingsBtnClick() {
        emitNewEffect(MoreEffect.navigateToMyRatings)
    }

    private fun fetchCurrentUserDetails() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("Tag fetchCurrentUserDetails", " user is:   ")
                val user = getCurrentUserDetailsUseCase()
                Log.d("Tag fetchCurrentUserDetails", " user is:   $user")
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
                        profilePictureUrl = null
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

    override fun onClickTheme() {
        updateState { it.copy(isThemeSheetVisible = true) }
    }

    override fun onConfirmTheme(themeType: ThemeType) {
        tryToExecute(
            function = { setAppThemeUseCase(themeType.toAppTheme()) },
            onSuccess = {
                updateState { it.copy(selectedTheme = themeType) }
            },
            onError = { onError() },
        )
    }

    override fun onClickLanguage() {
        updateState { it.copy(isLanguageSheetVisible = true) }
    }

    override fun onConfirmLanguage(languageType: LanguageType) {
        tryToExecute(
            function = { setLanguageUseCase(languageType.toAppLanguage()) },
            onSuccess = {
                updateState { it.copy(selectedLanguage = languageType) }
            },
            onError = { onError() },
        )
    }

    override fun onDismissBottomSheet() {
        updateState {
            it.copy(
                isThemeSheetVisible = false,
                isLanguageSheetVisible = false
            )
        }
    }

    override fun onLogoutBtnClick() {
        updateState { it.copy(isLogoutSheetVisible = true) }
    }

    fun dismissLogoutSheet() {
        updateState { it.copy(isLogoutSheetVisible = false) }
    }

    private fun getAppVersion(): String {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    private fun onError(message: String = "") {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }
}