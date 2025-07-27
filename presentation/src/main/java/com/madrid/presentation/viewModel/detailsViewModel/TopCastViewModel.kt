package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.mediaDeatailsUseCase.MovieDetailsUseCase
import com.madrid.domain.usecase.mediaDeatailsUseCase.SeriesDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class TopCastViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val seriesDetailsUseCase: SeriesDetailsUseCase,
) : BaseViewModel<MovieDetailsUiState, Nothing>(
    MovieDetailsUiState()
) {

    val args = savedStateHandle.toRoute<Destinations.TopCast>()

    init {
        Log.d("loadCast", "loadCast ... : ${args.mediaId}")
        loadCast()
    }

    private fun loadCast() {
        if (args.isMovie) {
            tryToExecute(
                function = { movieDetailsUseCase.getMovieCreditsById(args.mediaId) },
                onSuccess = { castList ->
                    val mappedCast = castList.map { castMember ->
                        MovieDetailsUiState.CastUiState(
                            id = castMember.id.toString(),
                            actorImageUrl = castMember.imageUrl,
                            actorName = castMember.name,
                        )
                    }
                    updateState {
                        it.copy(
                            cast = mappedCast,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onError = { error ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unknown error"
                        )
                    }
                }
            )
        } else {
            tryToExecute(
                function = { seriesDetailsUseCase.getSeriesCreditsById(args.mediaId) },
                onSuccess = { castList ->
                    val mappedCast = castList.map { castMember ->
                        MovieDetailsUiState.CastUiState(
                            id = castMember.id.toString(),
                            actorImageUrl = castMember.imageUrl,
                            actorName = castMember.name,
                        )
                    }
                    updateState {
                        it.copy(
                            cast = mappedCast,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onError = { error ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }

}