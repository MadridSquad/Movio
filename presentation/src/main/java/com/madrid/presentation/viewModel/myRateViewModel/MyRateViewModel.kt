package com.madrid.presentation.viewModel.myRateViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRateViewModel @Inject constructor(
    private val getUserRatedSeriesUseCase: GetUserRatedSeriesUseCase,
    private val getUserRatedMovieUseCase: GetUserRatedMovieUseCase
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
        updateState { it.copy(showLoadingScreen = true, isError = false) }

        viewModelScope.launch {
            tryToExecute(
                function = { loadData() },
                onSuccess = { result ->
                    updateState {
                        it.copy(
                            ratedMedia = result,
                            isError = false,
                            showLoadingScreen = false
                        )
                    }
                },
                onError = {
                    updateState {
                        it.copy(
                            isError = true,
                            showLoadingScreen = false
                        )
                    }
                },
            )
        }
    }

    private suspend fun loadData(): List<RatedMediaState> {
        val movie = getUserRatedMovieUseCase().map { it.toRatedMediaUiState() }
        val series = getUserRatedSeriesUseCase().map { it.toRatedMediaUiState() }
        return movie + series
    }

}