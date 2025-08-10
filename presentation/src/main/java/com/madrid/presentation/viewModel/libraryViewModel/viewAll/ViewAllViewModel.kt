package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.ViewAllStrategy
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.toMediaUiState
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
        loadAllItems()
    }

    private fun loadTitle() {
        updateState { it.copy(title = strategy.getTitle()) }
    }

    private fun loadAllItems() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { strategy.getAllItems() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        items = result.map { media -> media.toMediaUiState() },
                        isLoading = false
                    )
                }
            },
            onError = { }
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
                updateState { it.copy(items = it.items
                    .filterNot { item -> item.id == mediaId && item.mediaType == mediaType }) }
            },
            onError = { }
        )
    }

    override fun onRetryClicked() {
        loadTitle()
        loadAllItems()
    }

}