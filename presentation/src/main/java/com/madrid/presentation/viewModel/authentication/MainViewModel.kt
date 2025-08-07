package com.madrid.presentation.viewModel.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.CheckFirstLaunchUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.preferences.GetAppThemeUseCase
import com.madrid.domain.usecase.preferences.SetAppThemeUseCase
import com.madrid.domain.utils.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkFirstLaunchUseCase: CheckFirstLaunchUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase,
) : ViewModel() {

    var isLoggedIn = MutableStateFlow(false)
        private set

    var isLoading = mutableStateOf(true)
        private set

    var isFirstLaunch = MutableStateFlow(false)
        private set

    val isDarkTheme = MutableStateFlow(true)

    init {
        isLoggedIn()
        isFirstLaunch()
    }

    private fun isLoggedIn() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                loginUseCase.checkActiveSession().collectLatest { result ->
                    isLoggedIn.value = result
                    isLoading.value = false
                }
            }
        } catch (e: Exception) {
            isLoggedIn.value = false
            isLoading.value = false
        }
    }

    private fun isFirstLaunch() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val firstLaunch = checkFirstLaunchUseCase.isFirstLaunch().first()
                isFirstLaunch.value = firstLaunch

                if (firstLaunch) {
                    setAppThemeUseCase(AppTheme.DARK)
                }
                getAppThemeUseCase()
                    .map{it == AppTheme.DARK }
                    .collectLatest { isDark ->
                        isDarkTheme.value = isDark
                    }
            }
        } catch (e: Exception) {
            isFirstLaunch.value = true
        }
    }

    fun setOnBoardingCompleted(isCompleted: Boolean) {
        viewModelScope.launch {
            checkFirstLaunchUseCase.setOnBoardingDone(isCompleted = isCompleted)
        }
    }
}