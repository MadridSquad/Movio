package com.madrid.presentation.viewModel.detailsViewModel.similarMedia

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimilarMediaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarSeriesUseCase: GetSimilarSeriesUseCase,
) : BaseViewModel<SimilarMediaUiState, Nothing>(SimilarMediaUiState()) {

    private val args = savedStateHandle.toRoute<Destinations.SimilarMediaScreen>()

    init {
        if (args.isMovie) loadSimilarMedia()
        else loadSimilarSeries()
    }

    private fun loadSimilarMedia() {
        tryToExecute(
            function = {
                getSimilarMoviesUseCase(args.mediaId)
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
                getSimilarSeriesUseCase(args.mediaId)
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

fun List<Movie>.toMovieUiState(): List<MediaUiState> {
    return this.map { movie ->
        MediaUiState(
            mediaId = movie.id,
            isMovie = false,
            imageUrl = movie.imageUrl,
            mediaName = movie.title
        )
    }
}

fun List<Series>.toSeriesUiState(): List<MediaUiState> {
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


