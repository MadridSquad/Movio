package com.madrid.presentation.viewModel.libraryViewModel

import android.util.Log
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase,
    //getFavoriteUseCase: GetAllFavoriteMoviesUseCase,
    private val getHistoryUseCase: GetAllMoviesInHistoryUseCase,
) : BaseViewModel<LibraryScreenState, LibraryScreenEffect>(
    LibraryScreenState()
), LibraryInteractionListener {

    init {
        getWatchList()
        getFavoriteList()
        getHistoryList()
    }


    private fun getWatchList() {
        updateState {
            it.copy(
                isLoading = true,
            )
        }
        tryToExecute(
            function = {
                getWatchListUseCase()
            },
            onSuccess = {watchList->
                Log.i("MY_TAG","watchlist : ${watchList}")
                updateState {
                    it.copy(
                        isLoading = false,
                        watchList = watchList.map{ it.toWatchListState()}
                    )
                }
            },
            onError = {throwable->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message.toString()
                    )
                }
            },
        )
    }

    private fun getFavoriteList() {
        updateState {
            it.copy(
                isLoading = true,
            )
        }
        tryToExecute(
            function = {

            },
            onSuccess = {
                updateState {
                    it.copy(
                        isLoading = false,
                        favoriteList = emptyList()
                    )
                }
            },
            onError = {throwable->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message.toString()
                    )
                }
            },
        )
    }

    private fun getHistoryList() {
        updateState {
            it.copy(
                isLoading = true,
            )
        }
        tryToExecute(
            function = {
                getHistoryUseCase()
            },
            onSuccess = {historyList->
                updateState {
                    it.copy(
                        isLoading = false,
                        historyList = historyList.map{ it.toMediaUiState()}
                    )
                }
            },
            onError = {throwable->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message.toString()
                    )
                }
            },
        )

    }


    override fun onItemClick(itemId: Int) { // itemId may be a watchlist item or a media item(favorite or history)
//        viewModelScope.launch {
//            _effects.emit(LibraryScreenEffect.NavigateToMediaDetails(itemId))
//        }
    }

    override fun onViewAllClick(type: String) {  // type = watchlist or favorite or history
//        viewModelScope.launch {
//            _effects.emit(LibraryScreenEffect.NavigateToViewAll(type))
//        }
    }

}