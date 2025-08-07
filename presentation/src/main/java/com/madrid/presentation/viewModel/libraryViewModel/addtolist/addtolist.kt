package com.madrid.presentation.viewModel.libraryViewModel.addtolist

import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.WatchList
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.usecase.movie.AddMovieToListUseCase
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val createListSuccess: Boolean = false,
    val addToListSuccess: Boolean = false,
    val userLists: List<WatchList> = emptyList(),
    val isLoadingLists: Boolean = false // Add separate loading state for lists
)

sealed class MovieListEvent {
    object ClearMessages : MovieListEvent()
    object DismissNotification : MovieListEvent()
    object LoadUserLists : MovieListEvent() // Add this event
}

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getUserListsUseCase: suspend () -> List<WatchList>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<MovieListUiState, MovieListEvent>(MovieListUiState()) {

    // Add this method to load user lists
    fun loadUserLists() {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoadingLists = true, errorMessage = null) }

            try {
                val userLists = getUserListsUseCase()
                updateState {
                    it.copy(
                        userLists = userLists,
                        isLoadingLists = false
                    )
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
                        isLoadingLists = false,
                        errorMessage = getErrorMessage(ex)
                    )
                }
            }
        }
    }

    fun createMovieList(
        name: String,
        onSuccess: (() -> Unit)? = null
    ) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

            try {
                val status: ListOperationStatus = createMovieListUseCase(
                    name = name,
                    description = "My new list",
                    language = "en"
                )

                if (status.success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            createListSuccess = true,
                            successMessage = status.message
                        )
                    }
                    // Reload lists after creating a new one
                    loadUserLists()
                    onSuccess?.invoke()
                } else {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = status.message
                        )
                    }
                }
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

    fun addMovieToList(
        listId: Int,
        movieId: Int,
        onSuccess: (() -> Unit)? = null
    ) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

            try {
                val status: ListOperationStatus = addMovieToListUseCase(
                    listId = listId,
                    movieId = movieId
                )

                if (status.success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            addToListSuccess = true,
                            successMessage = status.message
                        )
                    }
                    onSuccess?.invoke()
                } else {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = status.message
                        )
                    }
                }
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

    fun updateUserLists(lists: List<WatchList>) {
        updateState { it.copy(userLists = lists) }
    }

    fun handleEvent(event: MovieListEvent) {
        when (event) {
            MovieListEvent.ClearMessages -> {
                updateState {
                    it.copy(
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }
            MovieListEvent.DismissNotification -> {
                updateState {
                    it.copy(
                        createListSuccess = false,
                        addToListSuccess = false,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }
            MovieListEvent.LoadUserLists -> {
                loadUserLists()
            }
        }
    }

    fun clearError() {
        updateState { it.copy(errorMessage = null) }
    }

    fun clearSuccess() {
        updateState {
            it.copy(
                successMessage = null,
                createListSuccess = false,
                addToListSuccess = false
            )
        }
    }

    private fun getErrorMessage(exception: MovioException): String {
        return when (exception) {
            is NetworkException -> "Network error. Please try again."
            else -> exception.message ?: "An unknown error occurred"
        }
    }
}