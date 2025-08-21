package com.madrid.presentation.viewModel.libraryViewModel.addtolist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.movie.AddMovieToListUseCase
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.base.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getWatchListsUseCase: GetWatchListsUseCase, // Changed: Use the actual use case class
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<MovieListUiState, MovieListEvent>(MovieListUiState()) {

    fun loadUserLists() {
        viewModelScope.launch(dispatcher) {
            updateState { it.copy(isLoadingLists = true, errorMessage = null) }

            tryToExecute(
                function = { getWatchListsUseCase() },
                onSuccess = { userLists ->
                    val uiLists = userLists.map { watchList ->
                        WatchListItemUiState(
                            id = watchList.id,
                            videosSize = watchList.itemCount ?: 0, // Assuming itemCount exists
                            watchListTitle = watchList.name
                        )
                    }
                    updateState {
                        it.copy(
                            watchListItems = uiLists, // Add UI-specific list
                            isLoadingLists = false
                        )
                    }
                },
                onError = { ex ->
                    updateState {
                        it.copy(
                            isLoadingLists = false,
                            errorMessage = getErrorMessage(ex)
                        )
                    }
                }
            )
        }
    }

    fun createMovieList(
        name: String
    ) {
        updateState { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
        tryToExecute(
            function = {
                createMovieListUseCase(
                    name = name,
                    description = "My new list",
                    language = "en"
                )
            },
            onSuccess = {
                updateState {
                    it.copy(
                        isLoading = false,
                        createListSuccess = true,
                        successMessage = R.string.new_list_created_successfully,
                    )
                }
                loadUserLists()
            },
            onError = { ex ->
                updateState {
                    it.copy(
                        isLoading = false,
                        createListSuccess = true,
                        errorMessage = ex.message
                    )
                }
            }
        )
    }

    fun addMovieToList(
        listId: Int,
        movieId: Int,
        onSuccess: (() -> Unit)? = null
    ) {
        updateState {
            it.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )
        }

        tryToExecute(
            function = {
                addMovieToListUseCase(
                    listId = listId,
                    movieId = movieId
                )
            },
            onSuccess = {
                updateState {
                    it.copy(
                        isLoading = false,
                        addToListSuccess = true,
                        successMessage = R.string.success_message
                    )
                }
                onSuccess?.invoke()
            },
            onError = { ex ->
                Log.d("MovieListViewModel", "addMovieToList: ${ex.message}")
                updateState {
                    it.copy(
                        isLoading = false,
                        addToListSuccess = true,
                        errorMessage = ex.message,
                    )
                }
            }
        )
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

    private fun getErrorMessage(exception: ErrorState): String {
        return exception.message
    }
}