package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.domain.entity.WatchList
import com.madrid.presentation.viewModel.shared.MediaUiState

data class LibraryScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val watchList: List<WatchListState> = listOf(),
    val favoriteList: List<MediaUiState> = listOf(),
    val historyList: List<MediaUiState> = listOf(),
    val refreshState: Boolean = false,
    val isGuest: Boolean = false,
)

data class WatchListState(
    val id: Int = 0,
    val numberOfVideos: Int = 0,
    val watchListTitle: String = "",
    val posterUrl: String? = null,
)


fun WatchList.toWatchListState(): WatchListState {
    return WatchListState(
        id = id,
        numberOfVideos = itemCount,
        watchListTitle = name,
        posterUrl = posterUrl,
    )
}

