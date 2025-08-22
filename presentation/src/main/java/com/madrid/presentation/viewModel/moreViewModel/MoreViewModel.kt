package com.madrid.presentation.viewModel.moreViewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.genre.ClearGenresCacheUseCase
import com.madrid.domain.usecase.movie.ClearHomeMoviesCacheUseCase
import com.madrid.domain.usecase.preferences.GetAppThemeUseCase
import com.madrid.domain.usecase.preferences.SetAppThemeUseCase
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
    private val clearHomeMoviesCacheUseCase: ClearHomeMoviesCacheUseCase,
    private val clearGenresCacheUseCase: ClearGenresCacheUseCase
) :
    BaseViewModel<MoreUiState, MoreEffect>(MoreUiState()),
    MoreInteractionListener {

    init {
        fetchIsGuest()
        fetchCurrentUserDetails()
        initAppTheme()
    }

    override fun onLoginBtnClick() {
        emitNewEffect(MoreEffect.navigateToLogin)
    }

    override fun onMyRatingsBtnClick() {
        emitNewEffect(MoreEffect.navigateToMyRatings)
    }

    private fun fetchCurrentUserDetails() {
        setLoading(true)
        tryToExecute(
            function = { getCurrentUserDetailsUseCase() },
            onSuccess = { user ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        username = user?.username ?: "",
                        profilePictureUrl = user?.profilePicUrl ?: ""
                    )
                }
            },
            onError = { e -> onError(message = e.message) }
        )
    }

    private fun fetchIsGuest() {
        tryToCollect(
            function = { isGuestUseCase.isGuest() },
            onNewValue = { result ->
                updateState { it.copy(isGuest = result) }
            },
            onError = { updateState { it.copy(isGuest = true) } },
        )
    }

    private fun initAppTheme() {
        tryToCollect(
            function = { getAppThemeUseCase() },
            onNewValue = { appTheme ->
                updateState {
                    it.copy(
                        selectedTheme = appTheme.toThemeType(),
                        currentTheme = appTheme.toThemeType()
                    )
                }
            },
            onError = { updateState { it.copy(isGuest = true) } },
        )
    }

    override fun onClickTheme() {
        updateState { it.copy(isThemeSheetVisible = true) }
    }

    override fun onSelectTheme(themeType: ThemeType) {
        updateState { it.copy(selectedTheme = themeType) }
    }

    override fun onConfirmTheme() {
        tryToExecute(
            function = { setAppThemeUseCase(state.value.selectedTheme.toAppTheme()) },
            onSuccess = {
                updateState { it.copy(currentTheme = state.value.selectedTheme) }
                onDismissBottomSheet()
            },
            onError = { onError() },
        )
    }

    override fun onClickLanguage() {
        updateState { it.copy(isLanguageSheetVisible = true) }
    }

    override fun onConfirmLanguage() {
        tryToExecute(
            function = {
                clearHomeMoviesCacheUseCase()
                clearGenresCacheUseCase()
            },
            onSuccess = {
                updateState { it.copy(isLanguageSheetVisible = false) }
            },
            onError = { onError() }
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

    private fun onError(message: String = "") {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = message,
                username = "Guest",
                profilePictureUrl = null
            )
        }
    }

    fun setLoading(isLoading: Boolean = true){
        updateState { it.copy(isLoading = isLoading) }
    }
}