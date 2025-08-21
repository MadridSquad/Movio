package com.madrid.presentation.viewModel.seeAll.tvShows

import com.madrid.presentation.viewModel.shared.CategoryUiState

interface SeeAllTVShowsInteractionListener {
    fun onSeriesClick(seriesId:Int)
    fun onGenreSelect(genre: CategoryUiState? = null)
    fun onTryAgainClick()
    fun onBackClick()
    fun onClickAllChip()
}