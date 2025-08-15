package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import com.madrid.presentation.viewModel.shared.MediaType

sealed interface ViewAllEffect {
    data object NavigateBack : ViewAllEffect
    data class NavigateToDetails(val mediaId: String, val mediaType: MediaType) : ViewAllEffect
}