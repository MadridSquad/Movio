package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.presentation.viewModel.shared.MediaType

interface MyRatingInteractionListener {
    fun onMediaClick(mediaId:Int,mediaType: MediaType)
    fun onBackClick()
}