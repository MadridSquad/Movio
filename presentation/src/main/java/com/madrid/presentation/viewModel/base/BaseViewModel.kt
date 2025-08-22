package com.madrid.presentation.viewModel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.madrid.domain.exceptions.AlreadyExistsException
import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.NotFoundException
import com.madrid.domain.exceptions.ServerException
import com.madrid.domain.exceptions.TimeoutException
import com.madrid.domain.exceptions.UnauthorizedException
import com.madrid.domain.exceptions.ValidationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()


    private val _effects = MutableSharedFlow<E>()
    val effect = _effects.asSharedFlow()

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: ((T) -> Unit)? = null,
        onError: (ErrorState) -> Unit,
        scope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job = runWithErrorHandling(onError, scope, dispatcher) {
        function().let { result ->
            onSuccess?.invoke(result)
        }
    }

    protected fun <T> tryToCollect(
        function: suspend () -> Flow<T>,
        onNewValue: suspend (T) -> Unit,
        onError: (ErrorState) -> Unit,
        scope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job =
        runWithErrorHandling(onError, scope, dispatcher) {
            function().distinctUntilChanged().collectLatest {
                onNewValue(it)
            }
        }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected fun emitNewEffect(effect: E) {
        viewModelScope.launch(Dispatchers.Main) {
            _effects.emit(effect)
        }
    }

    private fun runWithErrorHandling(
        onError: (ErrorState) -> Unit,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        function: suspend () -> Unit,
    ): Job {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.d("BaseViewModel", "Exception caught: ${throwable.message}", throwable)
            val errorState = exceptionMapper(throwable)
            Log.e("BaseViewModel", "Error occurred: ${errorState.message}", throwable)
            onError(errorState)
        }
        return scope.launch(dispatcher + coroutineExceptionHandler) {
            function()
        }
    }

    private fun exceptionMapper(throwable: Throwable): ErrorState {
        return when (throwable) {
            is InvalidCredentialsException -> InvalidCredentialsError(message = "Invalid credentials")
            is ValidationException -> ValidationError(message = "Validation error")
            is UnauthorizedException -> UnauthorizedError(message = "Unauthorized")
            is TimeoutException -> ServerError(message = "Server error ,try again later")
            is ServerException -> ServerError(message = "Server error ,try again later")
            is AlreadyExistsException -> AlreadyExistsError(message = "This item already exists")
            is NetworkException -> NetworkError(message = "Network error")
            is NotFoundException -> NotFoundError(message = "Not found")
            is UnknownHostException -> NetworkError(message = "No internet connection")
            else -> UnknownError(message = "Unknown error")
        }
    }

    fun <T : Any> launchPagingRequest(
        pagingSourceFactory: () -> PagingSource<Int, T>,
        onStartLoading: () -> Unit = {},
        onSuccess: (Flow<PagingData<T>>) -> Unit,
        onError: (Throwable) -> Unit = {},
        config: PagingConfig = PagingConfig(pageSize = 20)
    ) {
        try {
            onStartLoading()
            val result = Pager(
                config = config,
                pagingSourceFactory = pagingSourceFactory
            ).flow.cachedIn(viewModelScope)

            onSuccess(result)

        } catch (e: Exception) {
            onError(e)
        }
    }

}