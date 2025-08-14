package com.madrid.presentation.viewModel.libraryViewModel.layout

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.movie.AddMovieToListUseCase
import com.madrid.domain.usecase.movie.RemoveMovieFromListUseCase
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchListDetailsViewModel @Inject constructor(
    private val getWatchListItemsUseCase: GetWatchListItemsUseCase,
    private val removeMovieFromList: RemoveMovieFromListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    saveStateHandle: SavedStateHandle
) : BaseViewModel<WatchListDetailsState, WatchListDetailsEffect>(WatchListDetailsState()),
    WatchListDetailsInteractionListener {
    val args = saveStateHandle.toRoute<Destinations.WatchListDetailsScreen>()

    init {
        loadWatchListDetails()
    }

    private fun loadWatchListDetails() {
        updateState {
            it.copy(
                isLoading = true,
                headerTitle = args.watchListTitle
            )
        }
        tryToExecute(
            function = { getWatchListItemsUseCase(args.watchListId) },
            onSuccess = { onGetWatchListDetailsSuccess(it) },
            onError = { onError(it) }
        )
    }

    private fun onGetWatchListDetailsSuccess(items: GetWatchListItemsUseCase.WatchListItems) {
        updateState {
            it.copy(
                isLoading = false,
                mediaItems = items.movies.map { movie -> movie.toMediaUiState() }
            )
        }
    }

    override fun onNavigateBack() {
        emitNewEffect(WatchListDetailsEffect.NavigateBack)
    }

    override fun onClickMovieItem(movieId: String) {
        emitNewEffect(WatchListDetailsEffect.NavigateToMovieDetails(movieId))
    }

    override fun onDeleteMovie(movieId: String) {
        tryToExecute(
            function = { removeMovieFromList(movieId.toInt(), args.watchListId) },
            onSuccess = { onDeleteMovieSuccess(movieId) },
            onError = { onError(it) }
        )
    }

    override fun onDismissSnackBar() {
        updateState { it.copy(isSnackBarVisible = false) }
    }

    override fun onClickUndoAction() {
        tryToExecute(
            function = {
                addMovieToListUseCase(
                    movieId = state.value.deletedMovieId.toInt(),
                    listId = args.watchListId
                )
            },
            onSuccess = {
                loadWatchListDetails()
                onDismissSnackBar()
            },
            onError = { onError(it) }
        )
    }

    override fun onClickTryAgainButton() {
        updateState { it.copy(errorMessage = null) }
        loadWatchListDetails()
    }

    private fun onDeleteMovieSuccess(movieId: String) {
        updateState { currentState ->
            currentState.copy(
                isSnackBarVisible = true,
                mediaItems = currentState.mediaItems.filterNot { it.id == movieId },
                deletedMovieId = movieId,
            )
        }
    }

    private fun onError(throwable: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = throwable.message,
            )
        }
    }
}