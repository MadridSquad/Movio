package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.movie.GetMovieReviewsUseCase
import com.madrid.domain.usecase.series.GetSeriesReviewsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class ReviewsScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getSeriesReviewsUseCase: GetSeriesReviewsUseCase
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
            function = { getMovieReviewsUseCase(args.mediaId) },
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
            function = { getSeriesReviewsUseCase(args.mediaId) },
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