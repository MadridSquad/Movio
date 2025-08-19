package com.madrid.domain.entity

data class WatchList(
    val id: Int,
    val name: String,
    val itemCount: Int = 0,
    val description: String = "",
    val posterUrl: String? = null,
    var isSelected: Boolean = false,
    val movieIds: List<Int> = emptyList(),
    var isLoading: Boolean = false
)


data class MovieListUiState(
    val userLists: List<WatchList> = emptyList(),
    val isLoadingLists: Boolean = false,
    val addToListSuccess: Boolean = false,
    val createListSuccess: Boolean = false,
    val removeFromListSuccess: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
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
