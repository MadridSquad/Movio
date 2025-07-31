package com.madrid.presentation.viewModel.seeAll.movies

interface SeeAllMoviesInteractionListener {
    fun onMovieClick(movieId:Int)
    fun onGenreSelect(genre: CategoryUiState? = null)
    fun onBackClick()
    fun onClickAllChip()
}