package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.presentation.viewModel.shared.MediaType

data class MyRateUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val ratedMedia: List<RatedMediaState> = emptyList(),
    val showLoadingScreen: Boolean = false
)

data class RatedMediaState(
    val imageUrL: String = "",
    val mediaTitle: String = "",
    val rate: String = "",
    val mediaType: MediaType=MediaType.MOVIE,
    val mediaId :Int
)