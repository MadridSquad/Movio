package com.madrid.presentation.viewModel.libraryViewModel.addtolist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.MovieListEvent
import com.madrid.domain.entity.MovieListUiState
import com.madrid.domain.entity.WatchList
import com.madrid.domain.entity.WatchListItemUiState
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.usecase.movie.AddMovieToListUseCase
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import com.madrid.domain.usecase.movie.RemoveMovieFromListUseCase
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getWatchListsUseCase: GetWatchListsUseCase, // Changed: Use the actual use case class
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<MovieListUiState, MovieListEvent>(MovieListUiState()) {

    fun loadUserLists() {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoadingLists = true, errorMessage = null) }

            try {
                val userLists = getWatchListsUseCase() // Changed: Call invoke() on the use case
                Log.d("in add to list view model","lists: ${userLists.first()}")

                val uiLists = userLists.map { watchList ->
                    WatchListItemUiState(
                        id = watchList.id,
                        videosSize = watchList.itemCount ?: 0, // Assuming itemCount exists
                        watchListTitle = watchList.name
                    )
                }

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

    fun removeMovieFromList(
        listId: Int,
        movieId: Int,
        onSuccess: (() -> Unit)? = null
    ) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(errorMessage = null, successMessage = null) }

            try {
                val status: ListOperationStatus = removeMovieFromListUseCase(
                    listId = listId,
                    movieId = movieId
                )

                if (status.success) {
                    // Update the UI state to reflect the removal
                    updateState { currentState ->
                        val updatedLists = currentState.userLists.map { watchList ->
                            if (watchList.id == listId) {
                                watchList.copy(
                                    movieIds = watchList.movieIds.filter { it != movieId },
                                    itemCount = watchList.itemCount - 1
                                )
                            } else {
                                watchList
                            }
                        }

                        currentState.copy(
                            userLists = updatedLists,
                            removeFromListSuccess = true,
                            successMessage = status.message
                        )
                    }
                    onSuccess?.invoke()
                } else {
                    updateState {
                        it.copy(
                            errorMessage = status.message
                        )
                    }
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
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
            updateState { it.copy( errorMessage = null, successMessage = null) }

            try {
                val status: ListOperationStatus = createMovieListUseCase(
                    name = name,
                    description = "My new list",
                    language = "en"
                )

                if (status.success) {
                    updateState {
                        it.copy(
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
                            errorMessage = status.message
                        )
                    }
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
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
            updateState { it.copy(errorMessage = null, successMessage = null) }

            try {
                val status: ListOperationStatus = addMovieToListUseCase(
                    listId = listId,
                    movieId = movieId
                )

                if (status.success) {
                    // Update the UI state to reflect the addition
                    updateState { currentState ->
                        val updatedLists = currentState.userLists.map { watchList ->
                            if (watchList.id == listId) {
                                watchList.copy(
                                    movieIds = watchList.movieIds + movieId,
                                    itemCount = watchList.itemCount + 1
                                )
                            } else {
                                watchList
                            }
                        }

                        currentState.copy(
                            userLists = updatedLists,
                            addToListSuccess = true,
                            successMessage = status.message
                        )
                    }
                    onSuccess?.invoke()
                } else {
                    updateState {
                        it.copy(
                            errorMessage = status.message
                        )
                    }
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
                        errorMessage = getErrorMessage(ex)
                    )
                }
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