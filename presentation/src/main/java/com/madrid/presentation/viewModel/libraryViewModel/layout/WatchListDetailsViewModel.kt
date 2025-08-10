package com.madrid.presentation.viewModel.libraryViewModel.layout

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchListDetailsViewModel @Inject constructor(
   private val getWatchListItemsUseCase: GetWatchListItemsUseCase,
    saveStateHandle: SavedStateHandle
) : BaseViewModel<WatchListDetailsState, WatchListDetailsEffect>(
    WatchListDetailsState()
) {
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
            function = {
                getWatchListItemsUseCase(args.watchListId)
            },
            onSuccess = {watchItemList->
                var media = watchItemList.movies.map{it.toMediaUiState()} +watchItemList.series.map{it.toMediaUiState()}
                updateState {
                  it.copy(
                      isLoading = false,
                      watchList =  media
                  )
                }
            },
            onError = {throwable->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message
                    )
                }
            },
        )
    }

}