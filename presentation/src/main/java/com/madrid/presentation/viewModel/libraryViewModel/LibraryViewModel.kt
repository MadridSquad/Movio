package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.domain.usecase.series.GetAllSeriesInHistoryUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase,
    private val getFavoriteUseCase: GetFavoriteMoviesUseCase,
    private val getMovieHistoryUseCase: GetAllMoviesInHistoryUseCase,
    private val getSeriesHistoryUseCase: GetAllSeriesInHistoryUseCase,
    private val isLoggedInUseCase: LoginUseCase,
    private val createMovieListUseCase: CreateMovieListUseCase
) : BaseViewModel<LibraryScreenState, LibraryScreenEffect>(
    LibraryScreenState()
), LibraryInteractionListener {

    fun loadData() {
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

    override fun onAddWatchListClicked() {
        updateState { it.copy(isCreateListBottomSheetVisible = true) }
    }

    override fun dismissCreateListBottomSheet() {
        updateState { it.copy(isCreateListBottomSheetVisible = false) }
    }

    override fun onCreateWatchListButtonClicked(name: String) {
        tryToExecute(
            function = { createMovieListUseCase(name) },
            onSuccess = { onCreateSuccess() },
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

    private fun onCreateSuccess() {
        updateState {
            it.copy(
                isCreateListBottomSheetVisible = false,
                isSnackBarVisible = true,
                snackBarMessage = R.string.new_list_created_successfully
            )
        }
        onRefresh()
    }

    override fun onDismissSnackBar() {
        updateState { it.copy(isSnackBarVisible = false) }
    }

    fun getIsGuest() {
        updateState {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        tryToCollect(
            function = {
                isLoggedInUseCase.isGuest()
            },
            onNewValue = { isGuest ->
                updateState {
                    it.copy(isGuest = isGuest, isLoading = false)
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
                getMovieHistoryUseCase().map { it.toMediaUiState() } +
                        getSeriesHistoryUseCase().map { it.toMediaUiState() }
            },
            onSuccess = { historyList ->
                updateState {
                    it.copy(
                        isLoading = false,
                        historyList = historyList
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