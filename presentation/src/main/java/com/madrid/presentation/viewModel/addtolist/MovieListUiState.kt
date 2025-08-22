package com.madrid.presentation.viewModel.addtolist

import com.madrid.presentation.R


data class MovieListUiState(
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val successMessage: Int? = R.string.add_to_list,
    val createListSuccess: Boolean = false,
    val addToListSuccess: Boolean = false,
    val watchListItems: List<WatchListItemUiState> = emptyList(),
    val isLoadingLists: Boolean = false
)

data class WatchListItemUiState(
    val id: Int = 0,
    val videosSize: Int = 0,
    val watchListTitle: String = "",
    val isLoading: Boolean = false,
    val isSelected: Boolean = false
)

sealed class MovieListEvent {
    object ClearMessages : MovieListEvent()
    object DismissNotification : MovieListEvent()
    object LoadUserLists : MovieListEvent()
}

