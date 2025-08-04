package com.madrid.presentation.viewModel.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.authentication.CheckFirstLaunchUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkFirstLaunchUseCase: CheckFirstLaunchUseCase
) : ViewModel() {

    var isLoggedIn = MutableStateFlow(false)
        private set

    var isLoading = mutableStateOf(true)
        private set

    var isFirstLaunch = MutableStateFlow(false)
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
                isFirstLaunch.value = checkFirstLaunchUseCase.isFirstLaunch().first()
            }
        } catch (e: Exception) {
            isFirstLaunch.value = true
        }
    }

    fun setOnBoardingCompleted(isCompleted: Boolean){
        viewModelScope.launch {
            checkFirstLaunchUseCase.setOnBoardingDone(isCompleted = isCompleted)
        }
    }
}