package com.madrid.presentation.viewModel.shared

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series

data class MediaUiState(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val rating: String = "",
    val category: List<String> = emptyList(),
    val mediaType: MediaType = MediaType.MOVIE
)

enum class MediaType {
    MOVIE,
    TV_SHOW
}

fun Movie.toMediaUiState() = MediaUiState(
    id = id.toString(),
    title = title,
    imageUrl = imageUrl,
    rating = rate.toString(),
    category = genre.map { it.name },
    mediaType = MediaType.MOVIE
)

fun Series.toMediaUiState() = MediaUiState(
    id = id.toString(),
    title = title,
    imageUrl = imageUrl,
    rating = rate.toString(),
    category = genre.map { it.name },
    mediaType = MediaType.TV_SHOW
)