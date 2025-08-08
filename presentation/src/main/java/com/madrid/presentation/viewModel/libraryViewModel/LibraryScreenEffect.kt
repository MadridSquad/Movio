package com.madrid.presentation.viewModel.libraryViewModel

sealed class LibraryScreenEffect {


    data class NavigateToMediaDetails(val mediaId: String) :
        LibraryScreenEffect()

    data class NavigateToWatchListDetails(val watchListId: Int , val watchListTitle:String ) :
        LibraryScreenEffect()


    data class NavigateToViewAll(val type: String) :
        LibraryScreenEffect()

}

