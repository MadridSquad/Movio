package com.madrid.presentation.viewModel.shared

import com.madrid.domain.entity.WatchList


data class WatchListUiState(
    val id: Int = 0,
    val numberOfVideos: Int = 0,
    val watchListTitle: String = "",
    val posterUrl: String? = null,
)


fun WatchList.toWatchListUiState(): WatchListUiState {
    return WatchListUiState(
        id = id,
        numberOfVideos = itemCount,
        watchListTitle = name,
        posterUrl = posterUrl,
    )
}