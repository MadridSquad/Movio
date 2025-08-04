package com.madrid.presentation.viewModel.logoutViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.usecase.authentication.LogoutUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<LogoutUiState, Nothing>(LogoutUiState()) {

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null) }

            try {
                logoutUseCase.execute()
                updateState {
                    it.copy(
                        isLoading = false,
                        logoutSuccess = true
                    )
                }
                onSuccess()
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(ex)
                    )
                }
            }
        }
    }

    fun clearError() {
        updateState { it.copy(errorMessage = null) }
    }

    private fun getErrorMessage(exception: MovioException): String {
        return when (exception) {
            is NetworkException -> "Network error. Please try again."
            else -> exception.message ?: "Failed to logout. Please try again."
        }
    }
}