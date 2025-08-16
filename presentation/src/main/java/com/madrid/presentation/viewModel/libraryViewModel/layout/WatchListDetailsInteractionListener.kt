package com.madrid.presentation.viewModel.libraryViewModel.layout

interface WatchListDetailsInteractionListener {
    fun onNavigateBack()
    fun onClickMovieItem(movieId: String)
    fun onDeleteMovie(movieId: String)
    fun onDismissSnackBar()
    fun onClickUndoAction()
    fun onClickTryAgainButton()
}