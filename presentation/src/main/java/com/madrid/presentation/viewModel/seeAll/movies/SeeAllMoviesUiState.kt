package com.madrid.presentation.viewModel.seeAll.movies

import androidx.paging.PagingData
import com.madrid.domain.entity.Genre
import com.madrid.presentation.viewModel.shared.CategoryUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SeeAllMoviesUiState(
    val title: String = "",
    val selectedGenre: String? = null,
    val genre: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val filteredMovies: Flow<PagingData<MoviesUiState>> = flow {},
)

data class MoviesUiState(
    val id: String = "",
    val imageUrl: String = "",
    val rate: String = "",
    val name: String = "",
    val genre: List<CategoryUiState> = emptyList()
)