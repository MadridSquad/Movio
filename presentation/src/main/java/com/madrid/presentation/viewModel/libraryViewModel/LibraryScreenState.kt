package com.madrid.presentation.viewModel.libraryViewModel

import androidx.annotation.StringRes
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.shared.MediaUiState
import com.madrid.presentation.viewModel.shared.WatchListUiState

data class LibraryScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val watchList: List<WatchListUiState> = listOf(),
    val favoriteList: List<MediaUiState> = listOf(),
    val historyList: List<MediaUiState> = listOf(),
    val refreshState: Boolean = false,
    val isGuest: Boolean = false,
    val isCreateListBottomSheetVisible: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    @StringRes val snackBarMessage: Int = R.string.new_list_created_successfully,
)

