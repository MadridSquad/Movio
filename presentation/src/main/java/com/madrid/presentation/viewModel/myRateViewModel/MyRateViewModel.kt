package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.movie.GetUserRatedMovie
import com.madrid.domain.usecase.series.GetUserRatedSeries
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRateViewModel @Inject constructor(
    private val getUserRatedSeries: GetUserRatedSeries,
    private val getUserRatedMovie: GetUserRatedMovie
) : BaseViewModel<MyRateUiState, MyRatingEffect>(MyRateUiState()),
    MyRatingInteractionListener {

    init {
        loadRatedMedia()
    }

    override fun onMediaClick(mediaId: Int, mediaType: MediaType) {
        emitNewEffect(effect = MyRatingEffect.NavigateToMediaDetails(mediaId, mediaType))
    }

    override fun onBackClick() {
        emitNewEffect(effect = MyRatingEffect.NavigateBack)
    }

    private fun loadRatedMedia() {
        tryToExecute(
            function = { loadData() },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        ratedMedia = result,
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState { it.copy(isLoading = true) }
            },
        )
    }

    private suspend fun loadData(): List<RatedMediaState> {
        val movie = getUserRatedMovie().map { it.toRatedMediaUiState() }
        val series = getUserRatedSeries().map { it.toRatedMediaUiState() }
        return movie + series
    }

}