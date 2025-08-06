package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.ViewAllStrategy
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ViewAllViewModel.Factory::class)
class ViewAllViewModel @AssistedInject constructor (
    // Add any use cases needed for this ViewModel
    @Assisted private val strategy: ViewAllStrategy,
): BaseViewModel<ViewAllUiState, ViewAllEffect>(ViewAllUiState()),
    ViewAllInteractionListener{

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
    }

}