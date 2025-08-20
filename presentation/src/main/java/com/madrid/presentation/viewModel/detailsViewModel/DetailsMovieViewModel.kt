package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.movie.AddMovieToHistoryUseCase
import com.madrid.domain.usecase.movie.AddRatingMoviesUseCase
import com.madrid.domain.usecase.movie.GetMovieDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieReviewsUseCase
import com.madrid.domain.usecase.movie.GetMovieTopCastUseCase
import com.madrid.domain.usecase.movie.GetMovieTrailersUseCase
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.domain.usecase.movie.IsFavoriteMovieUseCase
import com.madrid.domain.usecase.movie.SetMovieFavoriteStatusUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.utils.formatRate
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.utils.formatDate
import com.madrid.presentation.viewModel.shared.formatDuration
import com.madrid.presentation.viewModel.shared.parser.formatDateKotlinx
import com.madrid.presentation.viewModel.shared.parser.formatDateOfBirth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    private val getAddRatingMoviesUseCase: AddRatingMoviesUseCase,
    private val isGuestUseCase: LoginUseCase,
    private val setMovieFavoriteStatusUseCase: SetMovieFavoriteStatusUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase
) : BaseViewModel<DetailsMovieUiState, Nothing>(
    DetailsMovieUiState()
) {
    val args = saveStateHandle.toRoute<Destinations.MovieDetailsScreen>()

    init {
        fetchIsGuest()
        saveMovieToHistory()
        loadData()
        checkIfFavoriteMovie()
    }

    private fun saveMovieToHistory() {
        tryToExecute(
            function = { addMovieToHistoryUseCase(args.movieId) },
            onSuccess = {},
            onError = {}
        )
    }

    private fun loadData() {
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
                        rate = formatRate(movie.rate),
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
        tryToExecute(
            function = { getMovieTopCastUseCase(args.movieId) },
            onSuccess = { result ->
                val formattedResult = result.map { artist ->
                    artist.copy(dateOfBirth = formatDateOfBirth(artist.dateOfBirth))
                }
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
                        rating = formatRate(movie.rate)
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
        tryToExecute(
            function = {
                getMovieReviewsUseCase(args.movieId)
            },
            onSuccess = { domainReviews ->
                val formattedReviews: List<ReviewUiState> = domainReviews.map { review ->
                    review.toReviewUiState()
                }
                updateState { currentState ->
                    currentState.copy(reviews = formattedReviews)
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

    private fun fetchIsGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isGuestUseCase.isGuest().collectLatest { result ->
                    updateState { it.copy(isGuest = result) }
                }
            } catch (e: Exception) {
                updateState { it.copy(isGuest = true) }
            }
        }
    }

    fun onClickLoveIcon(movieId: Int) {
        tryToExecute(
            function = {
                setMovieFavoriteStatusUseCase(movieId, state.value.isLoved.not())
            },
            onSuccess = {
                updateState {
                    it.copy(isLoved = state.value.isLoved.not())
                }
            },
            onError = {},
        )
    }

    fun onPickRatingNumber(rating: Int) {
        updateState {
            it.copy(
                userRating = rating
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
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }
    private fun checkIfFavoriteMovie() {
        tryToExecute(
            function = { isFavoriteMovieUseCase(args.movieId) },
            onSuccess = { isFavorite ->
                updateState { it.copy(isLoved = isFavorite) }
            },
            onError = {
                updateState { it.copy(isLoved = false) }
            },
        )
    }

    fun addRating() {
        if (state.value.userRating == 0) {
            return
        }

        tryToExecute(
            function = {
                getAddRatingMoviesUseCase(
                    state.value.movieId,
                    state.value.userRating.toDouble() * 2
                )
            },
            onSuccess = {},
            onError = {},
        )
    }
}