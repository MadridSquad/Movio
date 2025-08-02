package com.madrid.presentation.viewModel.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.CheckFirstLaunchUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    private val checkFirstLaunchUseCase: CheckFirstLaunchUseCase
) : ViewModel() {

    var isLoggedIn = MutableStateFlow(false)
        private set

    var isLoading = mutableStateOf(true)
        private set

    var isFirstLaunch = MutableStateFlow(true)
        private set

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
                checkFirstLaunchUseCase().collectLatest { result ->
                    isFirstLaunch.value = result
                }
            }
        } catch (e: Exception) {
            isFirstLaunch.value = false
            isFirstLaunch.value = false
        }
    }

}