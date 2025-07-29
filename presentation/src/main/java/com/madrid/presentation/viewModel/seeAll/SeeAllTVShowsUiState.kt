package com.madrid.presentation.viewModel.seeAll

data class SeeAllTVShowsUiState(
    val title: String="",
    val selectedGenre: String? = null,
    val genre: List<String>? = emptyList(),
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val filteredSeries: List<SeriesUiState> = emptyList(),
)
data class SeriesUiState(
    val id: String = "",
    val imageUrl: String = "",
    val rate: String = "",
    val name: String = "",
    val genre: List<String>? = emptyList()
)
