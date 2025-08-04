package com.madrid.presentation.viewModel.homeViewModel

import com.madrid.presentation.viewModel.shared.MediaType

sealed interface HomeScreenEffect {
    data class NavigateToMediaDetails(val mediaId: Int, val mediaType: MediaType) : HomeScreenEffect

    data class NavigateToProfile(val message: String) : HomeScreenEffect
    data class NavigateToSeeAll(val mediaType: MediaType)
}