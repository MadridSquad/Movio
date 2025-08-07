package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.presentation.viewModel.shared.MediaType

sealed interface MyRatingEffect {
    data object NavigateBack:MyRatingEffect
    data class NavigateToMediaDetails(val mediaId :Int,val mediaType: MediaType):MyRatingEffect
}