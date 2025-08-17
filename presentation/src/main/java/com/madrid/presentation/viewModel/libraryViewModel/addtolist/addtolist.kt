package com.madrid.presentation.viewModel.libraryViewModel.addtolist

import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.WatchList
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.usecase.movie.AddMovieToListUseCase
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import com.madrid.domain.usecase.movie.RemoveMovieFromListUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
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
    val removeFromListSuccess: Boolean = false,
    val userLists: List<WatchList> = emptyList(),
    val watchListItems: List<WatchListItemUiState> = emptyList(),
    val isLoadingLists: Boolean = false
)

data class WatchListItemUiState(
    val id: Int = 0,
    val videosSize: Int = 0,
    val watchListTitle: String = ""
)

sealed class MovieListEvent {
    object ClearMessages : MovieListEvent()
    object DismissNotification : MovieListEvent()
    object LoadUserLists : MovieListEvent()
}

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getWatchListsUseCase: GetWatchListsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<MovieListUiState, MovieListEvent>(MovieListUiState()) {

    fun loadUserLists() {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoadingLists = true, errorMessage = null) }

            try {
                val userLists = getWatchListsUseCase()
                val uiLists = userLists.map { watchList ->
                    WatchListItemUiState(
                        id = watchList.id,
                        videosSize = watchList.itemCount ?: 0,
                        watchListTitle = watchList.name
                    )
                }

                updateState {
                    it.copy(
                        userLists = userLists,
                        watchListItems = uiLists,
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

    fun removeMovieFromList(mediaId: Int, listId: Int) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
            try {
                // Get updated lists first
                val userLists = getWatchListsUseCase()
                val targetList = userLists.find { it.id == listId }

                // Check if movie exists in the list using movieIds
                val isMovieInList = targetList?.movieIds?.contains(mediaId) ?: false

                if (!isMovieInList) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Movie not found in the list."
                        )
                    }
                    return@launch
                }

                // Proceed with removal
                val result = removeMovieFromListUseCase(mediaId, listId)
                if (result.success) {
                    updateState {
                        it.copy(
                            isLoading = false,
                            removeFromListSuccess = true,
                            successMessage = result.message ?: "Movie removed successfully"
                        )
                    }
                    loadUserLists() // Refresh lists
                } else {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Failed to remove from list"
                        )
                    }
                }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to remove from list"
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
        onSuccess: (() -> Unit)? = null,
    ) {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

            try {
                // Always try to add first, then handle the duplicate case
                val status: ListOperationStatus = addMovieToListUseCase(
                    listId = listId,
                    movieId = movieId
                )

                // DEBUG: Add logging to see what we're getting
                println("DEBUG AddMovie: success=${status.success}, message='${status.message}', full_status=$status")

                when {
                    status.success -> {
                        // Successfully added
                        updateState {
                            it.copy(
                                isLoading = false,
                                addToListSuccess = true,
                                successMessage = status.message ?: "Movie added to list successfully"
                            )
                        }
                        loadUserLists() // Refresh lists
                        onSuccess?.invoke()
                    }

                    // Check for duplicate entry error (status_code 8) - more comprehensive check
                    !status.success && (
                            status.message?.contains("Duplicate entry", ignoreCase = true) == true ||
                                    status.message?.contains("already exists", ignoreCase = true) == true ||
                                    status.message?.contains("status_code\":8", ignoreCase = true) == true ||
                                    status.toString().contains("status_code=8", ignoreCase = true)
                            ) -> {

                        // Movie already exists, so remove it instead
                        val removeResult = removeMovieFromListUseCase(movieId, listId)
                        if (removeResult.success) {
                            updateState {
                                it.copy(
                                    isLoading = false,
                                    removeFromListSuccess = true,
                                    successMessage = removeResult.message ?: "Movie removed from list successfully"
                                )
                            }
                            loadUserLists() // Refresh lists
                            onSuccess?.invoke()
                        } else {
                            updateState {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = removeResult.message ?: "Failed to remove movie from list"
                                )
                            }
                        }
                    }

                    else -> {
                        // Other error
                        updateState {
                            it.copy(
                                isLoading = false,
                                errorMessage = status.message ?: "Failed to add movie to list"
                            )
                        }
                    }
                }
            } catch (ex: MovioException) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = getErrorMessage(ex)
                    )
                }
            } catch (ex: Exception) {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: "An unexpected error occurred"
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
                addToListSuccess = false,
                removeFromListSuccess = false // Fixed: Added this missing flag
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