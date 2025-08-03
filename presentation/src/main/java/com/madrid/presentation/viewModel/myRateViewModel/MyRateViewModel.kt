package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.movie.GetUserRatedMovie
import com.madrid.domain.usecase.series.GetUserRatedSeries
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.MediaType


class MyRateViewModel(
    private val getUserRatedSeries: GetUserRatedSeries,
    private val getUserRatedMovie: GetUserRatedMovie
) : BaseViewModel<MyRateUiState, MyRatingEffect>(MyRateUiState()),
    MyRatingInteractionListener {

//    init {
//          loadRatedMedia()
//    }

    override fun onMediaClick(mediaId: Int, mediaType: MediaType) {
        emitNewEffect(effect = MyRatingEffect.NavigateToMediaDetails(mediaId, mediaType))
    }

    override fun onBackClick() {
        emitNewEffect(effect = MyRatingEffect.NavigateBack)
    }

    fun loadRatedMedia(accountId: Int) {
        tryToExecute(
            function = { loadData(accountId) },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        ratedMedia = result,
                        isLoading = false
                    )
                }
            },
            onError = {
                //TODO
            },
        )
    }

    private suspend fun loadData(accountId: Int): List<RatedMediaState> {
        val movie = getUserRatedMovie(accountId).map { it.toRatedMediaUiState() }
        val series = getUserRatedSeries(accountId).map { it.toRatedMediaUiState() }
        return movie + series
    }

}