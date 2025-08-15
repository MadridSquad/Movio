package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase,
    private val getFavoriteUseCase: GetFavoriteMoviesUseCase,
    private val getHistoryUseCase: GetAllMoviesInHistoryUseCase,
    private val isLoggedInUseCase: LoginUseCase,
) : BaseViewModel<LibraryScreenState, LibraryScreenEffect>(
    LibraryScreenState()
), LibraryInteractionListener {

    init {
        getWatchList()
        getFavoriteList()
        getHistoryList()
        getIsGuest()
    }

    fun onRefresh() {
        getWatchList()
        getFavoriteList()
        getHistoryList()
    }


    override fun onLoginBtnClick() {
        emitNewEffect(
            LibraryScreenEffect.NavigateToLogin
        )
    }

    fun getIsGuest() {
        tryToCollect(
            function = {
                isLoggedInUseCase.isGuest()
            },
            onNewValue = { isGuest ->
                updateState {
                    it.copy(isGuest = isGuest)
                }
            },
            onError = { throwable ->
                updateState {
                    it.copy(errorMessage = throwable.message.toString())
                }
            }
        )
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
            onSuccess = { watchList ->
                updateState {
                    it.copy(
                        isLoading = false,
                        watchList = watchList.map { it.toWatchListState() }
                    )
                }
            },
            onError = { throwable ->
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
            onSuccess = { favoriteList ->
                updateState {
                    it.copy(
                        isLoading = false,
                        favoriteList = favoriteList.map { it.toMediaUiState() }
                    )
                }
            },
            onError = { throwable ->
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
            onSuccess = { historyList ->
                updateState {
                    it.copy(
                        isLoading = false,
                        historyList = historyList.map { it.toMediaUiState() }
                    )
                }
            },
            onError = { throwable ->
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

    override fun onWatchListViewAllClick() {
        emitNewEffect(
            LibraryScreenEffect.NavigateWatchListToViewAll
        )
    }

    override fun onViewAllClick(type: ViewAllType) {
        emitNewEffect(
            LibraryScreenEffect.NavigateToViewAll(type)
        )
    }

}