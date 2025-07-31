package com.madrid.presentation.viewModel.seeAll.movies

import com.madrid.domain.entity.Genre

data class SeeAllMoviesUiState(
    val title: String="",
    val selectedGenre: String? = null,
    val genre: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val filteredMovies: List<MoviesUiState> = emptyList(),
)
data class MoviesUiState(
    val id: String = "",
    val imageUrl: String = "",
    val rate: String = "",
    val name: String = "",
    val genre: List<CategoryUiState> = emptyList()
)

data class CategoryUiState(
    val id: Int = 0,
    val name: String = ""
)

fun Genre.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        id = this.id,
        name = this.name
    )
}