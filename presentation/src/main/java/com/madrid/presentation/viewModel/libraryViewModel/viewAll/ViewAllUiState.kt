package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import androidx.annotation.StringRes
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

data class ViewAllUiState(
    val title: Int = R.string.favorites,
    val items: List<MediaUiState> = emptyList(),
    val emptyListMessage : Int = R.string.Start_adding_the_movies_and_shows_you_love,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val showSnackBar: Boolean = false,
    @StringRes val snackBarMessage: Int = R.string.Item_has_been_deleted,
    val deletedItemId: Int = 0,
    val deletedItemType: MediaType = MediaType.MOVIE
)
