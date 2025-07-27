package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.mediaDeatailsUseCase.MovieDetailsUseCase
import com.madrid.domain.usecase.mediaDeatailsUseCase.SeriesDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class ReviewsScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val seriesDetailsUseCase: SeriesDetailsUseCase,
) : BaseViewModel<ReviewsScreenUiState, Nothing>(ReviewsScreenUiState()) {

    private val args = savedStateHandle.toRoute<Destinations.ReviewsScreen>()

    init {
        loadReviews()
    }

    private fun loadReviews() {
        if (args.isMovie) loadMovieReviews()
        else loadSeriesReviews()
    }

    private fun loadMovieReviews() {
        tryToExecute(
            function = { movieDetailsUseCase.getMovieReviewsById(args.mediaId) },
            onSuccess = { reviews ->
                updateState {
                    it.copy(reviews = reviews.map { review ->
                        review.toUiState()
                    })
                }
            },
            onError = { e -> Log.d("TAG lol", "loadMovieReviews: ${e.message}") },
        )
    }

    private fun loadSeriesReviews() {
        tryToExecute(
            function = { seriesDetailsUseCase.getSeriesReviewsById(args.mediaId) },
            onSuccess = { reviews ->
                updateState {
                    it.copy(reviews = reviews.map { review ->
                        review.toUiState()
                    })
                }
            },
            onError = { e -> Log.d("TAG lol", "loadSeriesReviews: ${e.message}") },
        )

    }


}