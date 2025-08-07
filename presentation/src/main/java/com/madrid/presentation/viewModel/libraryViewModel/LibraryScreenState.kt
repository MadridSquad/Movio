package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.presentation.viewModel.shared.MediaUiState

data class LibraryScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val watchList: List<WatchListState> = listOf(),
    val favoriteList: List<MediaUiState> = listOf(),
    val historyList: List<MediaUiState> = listOf(),
)

data class WatchListState(
    val id: Int = 0,
    val numberOfVideos: Int = 0,
    val watchListTitle: String = ""
)


// waiting for domain
/*
fun WatchList.toWatchListState(): WatchListState {
   return WatchListState(
       id = id,
       numberOfVideos = numberOfVideos,
       watchListTitle = watchListTitle
   )
}
*/

