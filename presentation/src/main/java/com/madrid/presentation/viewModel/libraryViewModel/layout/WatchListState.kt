package com.madrid.presentation.viewModel.libraryViewModel.layout

import com.madrid.presentation.viewModel.shared.MediaUiState

data class WatchListDetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val headerTitle: String = "",
    val mediaItems: List<MediaUiState> = listOf(),
    val isSnackBarVisible: Boolean = false,
    val deletedMovieId: String = ""
)