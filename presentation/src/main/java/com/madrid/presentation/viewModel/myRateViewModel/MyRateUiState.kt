package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.presentation.viewModel.shared.MediaType

data class MyRateUiState(
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val ratedMedia: List<RatedMediaState> = emptyList(),
    val showLoadingScreen: Boolean = false
)

data class RatedMediaState(
    val mediaTitle: String = "",
    val rate: String = "",
    val mediaType: MediaType = MediaType.MOVIE,
    val mediaId: Int,
    val date: String,
    val imageUrl: String
)
