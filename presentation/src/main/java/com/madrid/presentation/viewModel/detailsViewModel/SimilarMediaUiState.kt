package com.madrid.presentation.viewModel.detailsViewModel

data class SimilarMediaUiState(
    val isMovie: Boolean = true,
    val headerName: String = "",
    val medias: List<MediaUiState> = emptyList()
)

data class MediaUiState(
    val mediaId: Int = 0,
    val isMovie: Boolean = true,
    val imageUrl: String = "",
    val mediaName: String = "",
    val rate: String = ""
)