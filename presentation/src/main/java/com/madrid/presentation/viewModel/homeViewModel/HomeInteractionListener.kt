package com.madrid.presentation.viewModel.homeViewModel

import com.madrid.presentation.viewModel.shared.CategoryUiState
import com.madrid.presentation.viewModel.shared.MediaType

interface HomeInteractionListener {
    fun onSelectTab(index: Int)
    fun onSelectCategory(category: CategoryUiState)
    fun onSelectSortingType(sortType: SortingType)
    fun onMediaSelected(mediaId: Int, mediaType: MediaType)
    fun onClickTryAgainButton()
    fun loadMoviesLayoutData()
    fun loadSeriesLayoutData()
    fun onClickProfile()
    fun onClickPlayButton(mediaIndex: Int, mediaType: MediaType)
    fun onRefresh()
}