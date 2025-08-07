package com.madrid.presentation.viewModel.libraryViewModel

sealed class LibraryScreenEffect {
    data class NavigateToMediaDetails(val mediaId: Int) :
        LibraryScreenEffect()

    data class NavigateToWatchListDetails(val watchListId: Int) :
        LibraryScreenEffect()

    // navigate to view all
    // type = (watchlist, favorite, history)
    data class NavigateToViewAll(val type : String) :
        LibraryScreenEffect()

}

