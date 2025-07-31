package com.madrid.presentation.viewModel.homeViewModel

interface HomeInteractionListener {
    fun onSelectTab()
    fun onSelectCategory(category: CategoryUiState)
    fun onSelectSortingType(sortType: SortingType)
    fun onMediaSelected(mediaId: Int)
}