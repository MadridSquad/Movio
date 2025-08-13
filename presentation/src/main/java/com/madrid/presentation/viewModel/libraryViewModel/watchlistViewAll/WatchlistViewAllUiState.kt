package com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll

import androidx.annotation.StringRes
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.libraryViewModel.WatchListState

data class WatchlistViewAllUiState(
    val watchLists: List<WatchListState> = emptyList(),
    val showCreateListBottomSheet: Boolean = false,
    val showSnackBar: Boolean = false,
    @StringRes val snackBarMessage: Int = R.string.new_list_created_successfully,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
