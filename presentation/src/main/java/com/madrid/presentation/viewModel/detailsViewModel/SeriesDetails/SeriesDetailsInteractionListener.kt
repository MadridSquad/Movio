package com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails

import com.madrid.presentation.viewModel.detailsViewModel.SeeAllType


interface SeriesDetailsInteractionListener {
    fun onBackButtonClick()
    fun onRateButtonClick()
    fun onFavoriteClick(seriesId: Int)
    fun onPlayItClick()
    fun onPickRatingNumber(rating: Int)
    fun onEpisodePlayItClick(
        seriesId: Int, seasonNumber: Int
        ,episodeNumber: Int, onTrailerLoaded: (String?) -> Unit
    )
    fun onSeeAllClick(seriesId: Int, seeAllType: SeeAllType)
    fun onActorCardClick(actorId:Int)
    fun onSimilarSeriesCardClick(seriesId: Int)
    fun onCurrentSeasonCardClick(seriesId: Int,seasonNumber:Int)
    fun onRetryButtonClick()
    fun onLoginButtonClick()
    fun onShareIconClick()
    fun onDismissShareBottomSheetClick()
    fun onShowAddRatingBottomSheetClick()
    fun onShowDoneRatingBottomSheetClick()
    fun onDismissShowDoneRatingBottomSheetClick()
    fun onDismissAddRatingBottomSheet()
    fun onShowSnackBar()
    fun onDismissSnackBar()
    fun onDismissLoginBottomSheet()
}