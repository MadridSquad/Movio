package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.movie.GetMovieTopCastUseCase
import com.madrid.domain.usecase.series.GetSeriesTopCastUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class TopCastViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieTopCastUseCase: GetMovieTopCastUseCase,
    private val getSeriesTopCastUseCase: GetSeriesTopCastUseCase
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
                function = { getMovieTopCastUseCase(args.mediaId) },
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
                function = { getSeriesTopCastUseCase(args.mediaId) },
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