package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.entity.SimilarMovie
import com.madrid.domain.entity.SimilarSeries
import com.madrid.domain.usecase.mediaDeatailsUseCase.MovieDetailsUseCase
import com.madrid.domain.usecase.mediaDeatailsUseCase.SeriesDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class SimilarMediaViewModel(
    savedStateHandle: SavedStateHandle,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val seriesDetailsUseCase: SeriesDetailsUseCase,
) : BaseViewModel<SimilarMediaUiState, Nothing>(SimilarMediaUiState()) {

    private val args = savedStateHandle.toRoute<Destinations.SimilarMediaScreen>()

    init {
        if (args.isMovie) loadSimilarMedia()
        else loadSimilarSeries()
    }

    private fun loadSimilarMedia() {
        tryToExecute(
            function = {
                movieDetailsUseCase.getSimilarMoviesById(args.mediaId)
            },
            onSuccess = { allMovies ->
                updateState {
                    it.copy(
                        headerName = "Similar Movies",
                        medias = allMovies.toMovieUiState(),
                        isMovie = true
                    )
                }
            },
            onError = { e ->
                Log.d("TAG lol", "loadCastData: ${e.message}")
            },
        )
    }

    private fun loadSimilarSeries() {
        tryToExecute(
            function = {
                seriesDetailsUseCase.getSimilarSeriesById(args.mediaId)
            },
            onSuccess = { allSeries ->
                updateState {
                    it.copy(
                        headerName = "Similar Series",
                        medias = allSeries.toSeriesUiState(),
                        isMovie = false
                    )
                }
            },
            onError = { e ->
                Log.d("TAG lol", "loadCastData: ${e.message}")
            },
        )
    }

}

fun List<SimilarMovie>.toMovieUiState(): List<MediaUiState> {
    return this.map { movie ->
        MediaUiState(
            mediaId = movie.id,
            isMovie = false,
            imageUrl = movie.imageUrl,
            mediaName = movie.title
        )
    }
}

fun List<SimilarSeries>.toSeriesUiState(): List<MediaUiState> {
    return this.map { series ->
        MediaUiState(
            mediaId = series.id,
            isMovie = false,
            imageUrl = series.imageUrl,
            mediaName = series.title,
            rate = series.rate.toString()
        )
    }
}


