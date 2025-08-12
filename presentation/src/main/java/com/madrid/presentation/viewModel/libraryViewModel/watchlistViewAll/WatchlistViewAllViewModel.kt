package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.libraryViewModel.LibraryScreenEffect
import com.madrid.presentation.viewModel.libraryViewModel.WatchListState
import com.madrid.presentation.viewModel.libraryViewModel.toWatchListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistViewAllViewModel @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase,
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

    override fun onItemClick(watchList : WatchListState) {
        emitNewEffect(
            WatchlistViewAllEffect.NavigateToDetails(
                watchListId = watchList.id,
                watchListTitle = watchList.watchListTitle
            )
        )
    }

    override fun onAddButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onCreateButtonClicked() {
        TODO("Not yet implemented")
    }
}