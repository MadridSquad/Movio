package com.madrid.presentation.viewModel.detailsViewModel.movie

interface MovieDetailsInteractionListener {
    fun onClickLoveIcon(movieId: Int)
    fun onPickRatingNumber(rating: Int)
    fun onDismissLoginBottomSheet()
}