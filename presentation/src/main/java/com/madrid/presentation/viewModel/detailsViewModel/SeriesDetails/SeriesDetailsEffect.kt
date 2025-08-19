package com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails


sealed interface SeriesDetailsEffect {
    data object NavigateBack : SeriesDetailsEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : SeriesDetailsEffect
    data object  NavigateToAuthenticationScreen:SeriesDetailsEffect
    data class NavigateToActorDetails(val actorId: Int) : SeriesDetailsEffect
    data class NavigateToEpisodesScreen(val seriesId: Int, val seasonNumber :Int) : SeriesDetailsEffect
}