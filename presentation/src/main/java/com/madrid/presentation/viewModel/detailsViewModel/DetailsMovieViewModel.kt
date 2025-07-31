package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.usecase.movie.GetMovieDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieReviewsUseCase
import com.madrid.domain.usecase.movie.GetMovieTopCastUseCase
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.parser.formatDateKotlinx
import com.madrid.presentation.viewModel.shared.parser.formatDateOfBirth
import com.madrid.presentation.viewModel.shared.formatDuration
import kotlinx.coroutines.Dispatchers
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailsMovieViewModel(
    saveStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieTopCastUseCase: GetMovieTopCastUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : BaseViewModel<DetailsMovieUiState, Nothing>(
    DetailsMovieUiState()
) {
    val args = saveStateHandle.toRoute<Destinations.MovieDetailsScreen>()

    init {
        loadData()
    }

     private fun loadData() {
        Log.d("TAG lol", "=== LOADING MOVIE DETAILS ===")
        tryToExecute(
            function = {
                getMovieDetailsUseCase.invoke(args.movieId)

            },
            onSuccess = { movie ->

                updateState {
                    it.copy(
                        movieId = movie.id,
                        topImageUrl = movie.imageUrl,
                        dataMovie = formatDateKotlinx(movie.releaseDate),
                        movieName = movie.title,
                        rate = RateFormatter.formatRate(movie.rate),
                        movieDuration = formatDuration(movie.movieDuration),
                        description = movie.description,
                        genreMovie = movie.genre.map { it.toString() },
                    )
                }

                loadCast()
                loadSimilarMovies()
                loadReviews()
            },
            onError = { error -> },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    private fun loadCast() {
        Log.d("TAG lol", "=== LOADING CAST ===")
        tryToExecute(
            function = { getMovieTopCastUseCase(args.movieId) },
            onSuccess = { result ->
                val formattedResult = result.map { artist ->
                    artist.copy(dateOfBirth = formatDateOfBirth(artist.dateOfBirth))
                }
                Log.d("TAG lol", "Cast loaded: ${formattedResult.size}")
                updateState {
                    it.copy(casts = formattedResult)
                }
            },
            onError = { error ->
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    private fun loadSimilarMovies() {
        Log.d("TAG lol", "=== LOADING SIMILAR MOVIES ===")
        tryToExecute(
            function = {
                getSimilarMoviesUseCase(args.movieId)
            },
            onSuccess = { domainMovies ->
                val presentationMovies = domainMovies.map { movie ->
                    SimilarMovie(
                        id = movie.id,
                        title = movie.title,
                        imageUrl = movie.imageUrl,
                        rating = RateFormatter.formatRate(movie.rate) // Format rate here too
                    )
                }
                updateState { currentState ->
                    currentState.copy(similarMovies = presentationMovies)
                }
            },
            onError = { error ->
                updateState { currentState ->
                    currentState.copy(similarMovies = emptyList())
                }
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    private fun loadReviews() {
        Log.d("REVIEW_DEBUG", ">>> loadReviews started <<<")
        tryToExecute(
            function = {
                getMovieReviewsUseCase(args.movieId)
            },
            onSuccess = { domainReviews ->
                val reviewUiStates = domainReviews.map { review ->
                    ReviewUiState(
                        reviewerName = review.reviewerName,
                        reviewerImageUrl = "",
                        rating = review.rate.toFloat(),
                        date = review.date,
                        content = review.comment,
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

}
