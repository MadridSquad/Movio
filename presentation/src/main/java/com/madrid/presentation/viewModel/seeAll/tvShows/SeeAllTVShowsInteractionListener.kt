package com.madrid.presentation.viewModel.seeAll.tvShows

interface SeeAllTVShowsInteractionListener {
    fun onSeriesClick(seriesId:Int)
    fun onGenreSelect(genre: CategoryUiState? = null)
    fun onBackClick()
    fun onClickAllChip()
}