package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.usecase.movie.AddMovieToHistoryUseCase
import com.madrid.domain.usecase.movie.GetMovieDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieReviewsUseCase
import com.madrid.domain.usecase.movie.GetMovieTopCastUseCase
import com.madrid.domain.usecase.movie.GetMovieTrailersUseCase
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.formatDuration
import com.madrid.presentation.viewModel.shared.parser.formatDateKotlinx
import com.madrid.presentation.viewModel.shared.parser.formatDateOfBirth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieTopCastUseCase: GetMovieTopCastUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val addMovieToHistoryUseCase: AddMovieToHistoryUseCase,
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase,
) : BaseViewModel<DetailsMovieUiState, Nothing>(
    DetailsMovieUiState()
) {
    val args = saveStateHandle.toRoute<Destinations.MovieDetailsScreen>()

    init {
        saveMovieToHistory()
        loadData()
    }

    private fun saveMovieToHistory() {
        tryToExecute(
            function = { addMovieToHistoryUseCase(args.movieId) },
            onSuccess = {},
            onError = {}
        )
    }

    private fun loadData() {
        Log.d("TAG lol", "=== LOADING MOVIE DETAILS ===")
        tryToExecute(
            function = {
                getMovieDetailsUseCase.invoke(args.movieId)

            },
            onSuccess = { movie ->

                updateState { it ->
                    it.copy(
                        movieId = movie.id,
                        topImageUrl = movie.imageUrl,
                        dataMovie = formatDateKotlinx(movie.releaseDate),
                        movieName = movie.title,
                        rate = RateFormatter.formatRate(movie.rate),
                        movieDuration = formatDuration(movie.movieDuration),
                        description = movie.description,
                        genreMovie = movie.genre.map { it.name },
                        isLoading = false
                    )
                }

                loadCast()
                loadSimilarMovies()
                loadReviews()
                loadTrailer()
            },
            onError = { error -> updateState { it.copy(isLoading = true) } },
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

    fun onClickLoveIcon(

    ) {
        updateState {
            it.copy(
                isLoved = !it.isLoved
            )
        }
    }
    private fun loadTrailer() {
        tryToExecute(
            function = { getMovieTrailersUseCase(args.movieId) },
            onSuccess = { trailers ->
                val trailerKey = trailers.firstOrNull()?.key
                if (trailerKey != null) {
                    updateState { it.copy(trailerKey = trailerKey) }
                }
            },
            onError = {
                // Log or handle error if needed
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }


}
