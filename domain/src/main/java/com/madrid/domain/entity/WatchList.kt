package com.madrid.domain.entity

data class WatchList(
    val id: Int,
    val name: String,
    val itemCount: Int = 0,
    val description: String = "",
    val posterUrl: String? = null,
    var isSelected: Boolean = false,

    var isLoading: Boolean = false
)


data class MovieListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val createListSuccess: Boolean = false,
    val addToListSuccess: Boolean = false,
    var isSelected: Boolean = false,

    val userLists: List<WatchList> = emptyList(),
    val watchListItems: List<WatchListItemUiState> = emptyList(),
    val isLoadingLists: Boolean = false
)

data class WatchListItemUiState(
    val id: Int = 0,
    val videosSize: Int = 0,
    val watchListTitle: String = ""
)

sealed class MovieListEvent {
    object ClearMessages : MovieListEvent()
    object DismissNotification : MovieListEvent()
    object LoadUserLists : MovieListEvent()
}
