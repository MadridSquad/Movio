package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.usecase.mediaDeatailsUseCase.MovieDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
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
    val args = saveStateHandle.toRoute<Destinations.MovieDetailsScreen>()

    init {
        Log.e("MY_TAG", "args: ${args.movieId}")
        loadData()
    }

    internal fun loadData() {
        Log.d("TAG lol", "=== LOADING MOVIE DETAILS ===")
        tryToExecute(
            function = {
                movieDetailsUseCase.getMovieDetailsById(args.movieId)
            },
            onSuccess = { movie ->

                updateState {
                    it.copy(
                        movieId = movie.id,
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

                loadCast()
                loadSimilarMovies()
                loadReviews()
            },
            onError = { error ->
                Log.e("TAG lol", "Movie details error: $error")
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    private fun loadCast() {
        Log.d("TAG lol", "=== LOADING CAST ===")
        tryToExecute(
            function = { movieDetailsUseCase.getMovieCreditsById(args.movieId) },
            onSuccess = { result ->
                Log.d("TAG lol", "Cast loaded: ${result.size}")
                updateState {
                    it.copy(casts = result)
                }
            },
            onError = { error ->
                Log.e("TAG lol", "Cast error: $error")
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    private fun loadSimilarMovies() {
        Log.d("TAG lol", "=== LOADING SIMILAR MOVIES ===")
        tryToExecute(
            function = {
                movieDetailsUseCase.getSimilarMoviesById(args.movieId)
            },
            onSuccess = { domainMovies ->
                Log.d("TAG lol", "Similar movies loaded: ${domainMovies.size}")
                val presentationMovies = domainMovies.map { movie ->
                    SimilarMovie(
                        id = movie.id,
                        title = movie.title,
                        imageUrl = movie.imageUrl ?: "",
                        rating = movie.rate ?: 0.0
                    )
                }
                updateState { currentState ->
                    currentState.copy(similarMovies = presentationMovies)
                }
                Log.d("TAG lol", "Similar movies state updated with ${presentationMovies.size} movies")
            },
            onError = { error ->
                Log.e("TAG lol", "Similar movies error: $error")
                // Update state with empty list on error
                updateState { currentState ->
                    currentState.copy(similarMovies = emptyList())
                }
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    private fun loadReviews() {
        Log.d("TAG lol", "=== LOADING REVIEWS ===")
        tryToExecute(
            function = {
                movieDetailsUseCase.getMovieReviewsById(args.movieId)
            },
            onSuccess = { domainReviews ->
                Log.d("TAG lol", "Reviews loaded: ${domainReviews.size}")

                if (domainReviews.isNotEmpty()) {
                    val firstReview = domainReviews.first()
                }

                val reviewUiStates = domainReviews.map { review ->
                    ReviewUiState(
                        reviewerName = review.reviewerName?: "Anonymous",
                        reviewerImageUrl = "",
                        rating = review.rate?.toFloat() ?: 0f,
                        date = review.dateOfRelease ?: "",
                        content = review.comment ?: ""
                    )
                }
                updateState { currentState ->
                    currentState.copy(reviews = reviewUiStates)
                }
            },
            onError = { error ->
                updateState { currentState ->
                    currentState.copy(reviews = emptyList())
                }
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    fun onClickLoveIcon(

    ){
        updateState {
            it.copy(
                isLoved = !it.isLoved
            )
        }
    }
}
