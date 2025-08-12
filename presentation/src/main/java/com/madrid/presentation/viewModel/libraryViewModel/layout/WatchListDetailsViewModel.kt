package com.madrid.presentation.viewModel.libraryViewModel.layout

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
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
            onError = ::onError
        )
    }

    private fun onGetWatchListDetailsSuccess(items: GetWatchListItemsUseCase.WatchListItems) {
        updateState {
            it.copy(
                isLoading = false,
                watchList = items.movies.map { movie -> movie.toMediaUiState() }
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
            onError = ::onError
        )
    }

    private fun onDeleteMovieSuccess(movieId: String) {
        updateState { currentState ->
            currentState.copy(
                watchList = currentState.watchList.filterNot { it.id == movieId }
            )
        }
    }

    private fun onError(throwable: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = throwable.message
            )
        }
    }
}