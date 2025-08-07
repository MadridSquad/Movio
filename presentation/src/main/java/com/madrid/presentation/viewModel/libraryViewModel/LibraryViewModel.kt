package com.madrid.presentation.viewModel.libraryViewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    // watchListUseCase: WatchListUseCase,
    // favoriteUseCase: WatchListUseCase,
    // historyUseCase: WatchListUseCase,
) : ViewModel(),LibraryInteractionListener {

    private val _state = MutableStateFlow(LibraryScreenState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<LibraryScreenEffect>()
    val effect = _effects.asSharedFlow()


    init {
        getWatchList()
        //getFavoriteList()
        //getHistoryList()
    }

    private fun getWatchList() {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _state.value = _state.value.copy(
                isLoading = false,
                errorMessage = throwable.message.toString()
            )
        }
        // viewModelScope.launch(Dispatchers.IO + exceptionHandler) {}
    }


    override fun onItemClick(itemId: Int) {

    }

    override fun onViewAllClick(type: String) {

    }

}