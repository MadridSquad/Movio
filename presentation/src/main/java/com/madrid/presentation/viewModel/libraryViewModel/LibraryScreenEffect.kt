package com.madrid.presentation.viewModel.libraryViewModel

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType

sealed class LibraryScreenEffect {
    data class NavigateToMediaDetails(val mediaId: String) :
        LibraryScreenEffect()

    data class NavigateToWatchListDetails(val watchListId: Int, val watchListTitle: String) :
        LibraryScreenEffect()

    data object NavigateWatchListToViewAll : LibraryScreenEffect()

    data class NavigateToViewAll(val type: ViewAllType) :
        LibraryScreenEffect()

    data object NavigateToLogin : LibraryScreenEffect()
}