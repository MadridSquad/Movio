package com.madrid.presentation.viewModel.seeAll.movies

import com.madrid.presentation.viewModel.shared.CategoryUiState

interface SeeAllMoviesInteractionListener {
    fun onMovieClick(movieId:Int)
    fun onGenreSelect(genre: CategoryUiState? = null)
    fun onBackClick()
    fun onClickAllChip()
    fun onTryAgainClick()
}