package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.base.ErrorState
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.ViewAllStrategy
import com.madrid.presentation.viewModel.shared.MediaType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ViewAllViewModel.Factory::class)
class ViewAllViewModel @AssistedInject constructor(
    @Assisted private val strategy: ViewAllStrategy,
) : BaseViewModel<ViewAllUiState, ViewAllEffect>(ViewAllUiState()),
    ViewAllInteractionListener {

    @AssistedFactory
    interface Factory {
        fun create(
            strategy: ViewAllStrategy,
        ): ViewAllViewModel
    }

    init {
        loadTitle()
        loadEmptyListMessage()
        loadAllItems()
    }

    private fun loadTitle() {
        updateState { it.copy(title = strategy.getTitle()) }
    }

    private fun loadEmptyListMessage() {
        updateState { it.copy(emptyListMessage = strategy.getEmptyListMessage()) }
    }

    private fun loadAllItems() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { strategy.getAllItems() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        items = result,
                        isLoading = false
                    )
                }
            },
            onError = { error -> onError(error) }
        )
    }

    override fun onBackClicked() {
        emitNewEffect(ViewAllEffect.NavigateBack)
    }

    override fun onItemClicked(mediaId: String, mediaType: MediaType) {
        emitNewEffect(ViewAllEffect.NavigateToDetails(mediaId, mediaType))
    }

    override fun onItemDeleted(mediaId: String, mediaType: MediaType) {
        tryToExecute(
            function = { strategy.deleteItem(mediaId, mediaType) },
            onSuccess = {
                updateState {
                    it.copy(
                        deletedItemId = mediaId.toInt(),
                        deletedItemType = mediaType,
                        showSnackBar = true,
                        snackBarMessage = R.string.Item_has_been_deleted,
                        items = it.items
                            .filterNot { item -> item.id == mediaId && item.mediaType == mediaType })
                }
            },
            onError = { error -> onError(error) }
        )
    }

    override fun onUndoDeleteClicked(
    ) {
        tryToExecute(
            function = {
                strategy.onUndoDelete(
                    state.value.deletedItemId,
                    state.value.deletedItemType
                )
            },
            onSuccess = {
                loadAllItems()
            },
            onError = { error -> onError(error) }
        )
    }


    override fun onRetryClicked() {
        loadTitle()
        loadAllItems()
    }

    override fun onDismissSnackBar() {
        updateState { it.copy(showSnackBar = false) }
    }

    override fun onTryAgainButtonClicked() {
        updateState { it.copy(errorMessage = null) }
        loadAllItems()
    }

    private fun onError(error: ErrorState) {
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = error.message.toString()
            )
        }
    }

}