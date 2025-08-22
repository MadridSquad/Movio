package com.madrid.presentation.viewModel.homeViewModel

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.shared.CategoryUiState
import com.madrid.presentation.viewModel.shared.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeScreenState(
    val isLoading: Boolean = false,
    val refreshState: Boolean = false,
    val errorMessage: String = "",
    val profileImage: String? = null,
    val allTabUiState: AllTabUiState = AllTabUiState(),
    val movieTabUiState: MovieTabUiState = MovieTabUiState(),
    val tvShowTabUiState: TvShowTabUiState = TvShowTabUiState(),
    val categoryTabUiState: CategoryTabUiState = CategoryTabUiState(),
    val selectedTabIndex: Int = 0
)

data class AllTabUiState(
    val topRated: SectionUiState = SectionUiState(),
    val trending: SectionUiState = SectionUiState(),
    val freeToWatch: SectionUiState = SectionUiState(),
    val upcoming: SectionUiState = SectionUiState(),
    val moreRecommended: SectionUiState = SectionUiState(),
)

data class MovieTabUiState(
    val trending: SectionUiState = SectionUiState(),
    val topRated: SectionUiState = SectionUiState(),
    val nowPlaying: SectionUiState = SectionUiState(),
    val upcoming: SectionUiState = SectionUiState(),
    val moreRecommended: SectionUiState = SectionUiState(),
)

data class TvShowTabUiState(
    val trending: SectionUiState = SectionUiState(),
    val topRated: SectionUiState = SectionUiState(),
    val airingToday: SectionUiState = SectionUiState(),
    val onTv: SectionUiState = SectionUiState(),
    val moreRecommended: SectionUiState = SectionUiState(),
)

data class CategoryTabUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryUiState> = emptyList(),
    val selectedCategory: CategoryUiState? = CategoryUiState(),
    val media: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val sortingType: SortingType = SortingType.ALL,
)

data class SectionUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val media: List<MediaUiState> = emptyList()
)

enum class SortingType(@StringRes val value: Int) {
    ALL(R.string.all),
    POPULARITY(R.string.popularity),
    LATEST(R.string.latest),
}