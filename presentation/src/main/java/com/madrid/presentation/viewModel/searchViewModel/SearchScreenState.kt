package com.madrid.presentation.viewModel.searchViewModel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.madrid.presentation.viewModel.shared.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Immutable
data class SearchScreenState(
    val searchUiState: SearchUiState = SearchUiState(),
    val recentSearchUiState: List<String> = emptyList(),
    val allRecentSearchTexts : List<String> = emptyList(),
    val isDoAction : Boolean = false,
    val filteredScreenUiState: FilteredScreenUiState = FilteredScreenUiState()
) {
    data class SearchUiState(
        val forYouMovies: List<MovieUiState> = emptyList(),
        val exploreMoreMovies: Flow<PagingData<MovieUiState>> = flow {},
        val searchResults: Flow<PagingData<MovieUiState>> = flow {},
        val searchQuery : String = "",
        val isSearchQueryChange : Boolean = false,
        val isError: Boolean = false,
        val isLoading: Boolean = false,
        val refreshState: Boolean = false,
        val errorMessage: String? = null
    )

    data class FilteredScreenUiState(
        val searchResultCount: String = "1",
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val topResult: Flow<PagingData<MovieUiState>> = flow {},
        val movie: Flow<PagingData<MovieUiState>> = flow {},
        val series: Flow<PagingData<SeriesUiState>> = flow {},
        val artist: Flow<PagingData<ArtistUiState>> = flow {},
    )

    data class MovieUiState(
        val id: String = "",
        val mediaType: MediaType = MediaType.MOVIE,
        val title: String = "",
        val imageUrl: String = "",
        val rating: String = "",
        val category: String = "",
    )

    data class SeriesUiState(
        val id: String = "",
        val mediaType: MediaType = MediaType.TV_SHOW,
        val title: String = "",
        val imageUrl: String = "",
        val rating: String = "",
        val category: String = "",
    )

    data class ArtistUiState(
        val id: String = "",
        val name: String = "",
        val role: String = "",
        val country: String? = null,
        val description: String? = null,
        val imageUrl: String = "",
    )

}