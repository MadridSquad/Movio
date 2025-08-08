package com.madrid.presentation.viewModel.shared

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.presentation.viewModel.homeViewModel.CategoryUiState
import com.madrid.presentation.viewModel.homeViewModel.toCategoryUiState

data class MediaUiState(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val rating: String = "",
    val category: List<CategoryUiState> = emptyList(),
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
    rating = rate.toString().take(3),
    category = genre.map { it.toCategoryUiState() },
    mediaType = MediaType.MOVIE
)

fun Series.toMediaUiState() = MediaUiState(
    id = id.toString(),
    title = title,
    imageUrl = imageUrl,
    rating = rate.toString().take(3),
    category = genre.map { it.toCategoryUiState() },
    mediaType = MediaType.TV_SHOW
)