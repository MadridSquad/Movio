package com.madrid.presentation.viewModel.homeViewModel

data class TrendingUiState (
    val isLoading: Boolean = false,
    val trending: List<Trending> = emptyList(),
    val errorMessage: String = ""
)

data class Trending(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val mediaType: String,
)