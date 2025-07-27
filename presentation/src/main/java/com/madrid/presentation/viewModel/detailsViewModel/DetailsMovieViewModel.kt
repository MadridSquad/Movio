package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.usecase.mediaDeatailsUseCase.MovieDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class DetailsMovieViewModel(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val saveStateHandle: SavedStateHandle
) : BaseViewModel<DetailsMovieUiState, Nothing>(
    DetailsMovieUiState()
) {

    //MovieDetailsScreen
    val args = saveStateHandle.toRoute<Destinations.MovieDetailsScreen>()

    init {
        Log.e("MY_TAG", "args: ${args.movieId}")
        loadData()
        LoadCast()
    }


    internal fun loadData() {
        tryToExecute(
            function = {
                movieDetailsUseCase.getMovieDetailsById(args.movieId)
            },

            onSuccess = { movie ->
                Log.d("cast details", "loadData: ${movie.crew}")

                updateState {
                    it.copy(
                        movieId = movie.id.toString(),
                        topImageUrl = movie.imageUrl,
                        dataMovie = movie.yearOfRelease,
                        movieName = movie.title,
                        rate = movie.rate.toString(),
                        movieDuration = movie.movieDuration,
                        description = movie.description,
                        genreMovie = movie.genre ?: emptyList(),
                        casts = movie.crew,
                    )
                }
            },
            onError = {

            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    fun LoadCast() {
        tryToExecute(
            function = { movieDetailsUseCase.getMovieCreditsById(args.movieId) },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        casts = result
                    )
                }
            },
            onError = {},
        )
    }
}
