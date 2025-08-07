package com.madrid.presentation.viewModel.seeAll.tvShows

import androidx.paging.PagingData
import com.madrid.domain.entity.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SeeAllTVShowsUiState(
    val title: String="",
    val selectedGenre: String? = null,
    val genre: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val filteredSeries: Flow<PagingData<SeriesUiState>> =  flow {},
)
data class SeriesUiState(
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
