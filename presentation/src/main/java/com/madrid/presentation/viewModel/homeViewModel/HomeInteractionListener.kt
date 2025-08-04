package com.madrid.presentation.viewModel.homeViewModel

import com.madrid.presentation.viewModel.shared.MediaType

interface HomeInteractionListener {
    fun onSelectTab()
    fun onSelectCategory(category: CategoryUiState)
    fun onSelectSortingType(sortType: SortingType)
    fun onMediaSelected(mediaId: Int, mediaType: MediaType)
    fun loadMoviesLayoutData()
    fun loadSeriesLayoutData()
}