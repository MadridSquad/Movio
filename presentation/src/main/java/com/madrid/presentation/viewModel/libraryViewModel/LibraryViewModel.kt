package com.madrid.presentation.viewModel.libraryViewModel

import android.util.Log
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase,
    private val getFavoriteUseCase: GetFavoriteMoviesUseCase,
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
                getFavoriteUseCase()
            },
            onSuccess = {favoriteList->
                updateState {
                    it.copy(
                        isLoading = false,
                        favoriteList = favoriteList.map{ it.toMediaUiState()}
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


    override fun onItemClick(itemId: String) {
        emitNewEffect(
            LibraryScreenEffect.NavigateToMediaDetails(itemId)
        )
    }

    override fun onItemWatchListClick(watchListItem: WatchListState) {
        emitNewEffect(
            LibraryScreenEffect.NavigateToWatchListDetails(
                watchListId = watchListItem.id,
                watchListTitle = watchListItem.watchListTitle
            )
        )
    }

    override fun onViewAllClick(type: String) {
        emitNewEffect(
            LibraryScreenEffect.NavigateToViewAll(type)
        )
    }

}