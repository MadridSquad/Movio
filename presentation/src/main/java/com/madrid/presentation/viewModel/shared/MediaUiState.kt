package com.madrid.presentation.viewModel.shared

data class MediaUiState(
    val id: String,
    val mediaType: MediaType,
    val title: String,
    val imageUrl: String,
    val rating: String,
    val category: String
)

enum class MediaType {
    MOVIE,
    TV_SERIES
}