package com.madrid.presentation.viewModel.addtolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.UserList
import com.madrid.domain.usecase.movie.AddMovieToListUseCase
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ListUiState {
    object Idle : ListUiState()
    object Loading : ListUiState()
    data class Success(val message: String) : ListUiState()
    data class Error(val message: String) : ListUiState()
}

class MovieListViewModel(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Idle)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _userLists = MutableStateFlow<List<UserList>>(emptyList())
    val userLists: StateFlow<List<UserList>> = _userLists.asStateFlow()

    fun createMovieList(sessionId: String, name: String) {
        Log.d("MovieListViewModel", "Attempting to create list with sessionId: $sessionId and name: $name")
        _uiState.value = ListUiState.Loading
        viewModelScope.launch {
            try {
                val status: ListOperationStatus = createMovieListUseCase(sessionId, name, "My new list", "en")
                if (status.success) {
                    Log.d("MovieListViewModel", "Successfully created list: ${status.message}")
                    _uiState.value = ListUiState.Success(status.message)
                } else {
                    Log.e("MovieListViewModel", "Failed to create list: ${status.message}")
                    _uiState.value = ListUiState.Error(status.message)
                }
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "Error creating list: ${e.message}", e)
                _uiState.value = ListUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun addMovieToList(sessionId: String, listId: Int, movieId: Int) {
        Log.d("MovieListViewModel", "Attempting to add movie to list. sessionId: $sessionId, listId: $listId, movieId: $movieId")
        _uiState.value = ListUiState.Loading
        viewModelScope.launch {
            try {
                val status: ListOperationStatus = addMovieToListUseCase(
                    listId = listId,
                    sessionId = sessionId,
                    movieId = movieId
                )
                if (status.success) {
                    Log.d("MovieListViewModel", "Successfully added movie to list: ${status.message}")
                    _uiState.value = ListUiState.Success(status.message)
                } else {
                    Log.e("MovieListViewModel", "Failed to add movie to list: ${status.message}")
                    _uiState.value = ListUiState.Error(status.message)
                }
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "Error adding movie to list: ${e.message}", e)
                _uiState.value = ListUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun onDismissNotification() {
        Log.d("MovieListViewModel", "Dismissing notification")
        _uiState.value = ListUiState.Idle
    }
}