package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

import android.provider.Settings.Global.getString
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.libraryViewModel.LibraryScreenEffect
import com.madrid.presentation.viewModel.libraryViewModel.WatchListState
import com.madrid.presentation.viewModel.libraryViewModel.toWatchListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistViewAllViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase,
    private val createMovieListUseCase: CreateMovieListUseCase,
//    private val deleteWatchListUseCase: DeleteWatchListUseCase
) : BaseViewModel<WatchlistViewAllUiState, WatchlistViewAllEffect>(WatchlistViewAllUiState()),
    WatchListViewAllInteractionListener {

    init {
        loadWatchLists()
    }

    fun loadWatchLists() {
        updateState {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        tryToExecute(
            function = { getWatchListUseCase() },
            onSuccess = { watchLists ->
                updateState {
                    it.copy(
                        watchLists = watchLists.map { list -> list.toWatchListState() },
                        isLoading = false,
                        errorMessage = null
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message.toString()
                    )
                }
            }
        )
    }

    override fun onBackButtonClicked() {
        emitNewEffect(
            WatchlistViewAllEffect.NavigateBack
        )
    }

    override fun onItemClick(watchList: WatchListState) {
        emitNewEffect(
            WatchlistViewAllEffect.NavigateToDetails(
                watchListId = watchList.id,
                watchListTitle = watchList.watchListTitle
            )
        )
    }

    override fun onAddButtonClicked() {
        updateState { it.copy(showCreateListBottomSheet = true) }
    }

    override fun dismissCreateListBottomSheet() {
        updateState { it.copy(showCreateListBottomSheet = false) }
    }

    override fun onCreateButtonClicked(name: String) {
        tryToExecute(
            function = { createMovieListUseCase(name) },
            onSuccess = {
                updateState {
                    it.copy(
                        showCreateListBottomSheet = false,
                        showSnackBar = true,
                        snackBarMessage = R.string.new_list_created_successfully
                    )
                }
                loadWatchLists()
            },
            onError = { error ->
                updateState {
                    it.copy(
                        showCreateListBottomSheet = false,
                        errorMessage = error.message.toString()
                    )
                }
            }
        )
    }

    override fun onDismissSnackBar() {
        updateState { it.copy(showSnackBar = false) }
    }
}