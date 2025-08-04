package com.madrid.presentation.viewModel.seeAll.movies

import android.util.Log
import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel(assistedFactory = SeeAllMoviesViewModel.Factory::class)
class SeeAllMoviesViewModel @AssistedInject constructor(
    private val getMoviesGenresUseCase: GetMovieGenresUseCase,
    @Assisted private val strategy: SeeAllMoviesStrategy,
) : BaseViewModel<SeeAllMoviesUiState, SeeAllEffect>(SeeAllMoviesUiState()),
    SeeAllMoviesInteractionListener {

    @AssistedFactory
    interface Factory {
        fun create(
            strategy: SeeAllMoviesStrategy,
        ): SeeAllMoviesViewModel
    }
    init {
        Log.d("TAG zoz", "in view model init")
        loadTitle()
        loadGenres()
        loadAllMovies()
    }

    private fun loadTitle() {
        updateState { it.copy(title = strategy.getTitle()) }
    }

    private fun loadGenres() {
        tryToExecute(
            function = { getMoviesGenresUseCase() },
            onSuccess = { genres ->
                Log.d("TAG zoz", "in view model init")
                updateState { it.copy(genre = genres.map { genre -> genre.toCategoryUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    private fun loadAllMovies() {
        tryToExecute(
            function = { strategy.getAllMovies(1) },
            onSuccess = { movies ->
                updateState { it.copy(filteredMovies = movies.map { movie -> movie.toUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }


    override fun onGenreSelect(genre: CategoryUiState?) {
        if (genre == null)
            loadAllMovies()
        else {
            Log.d("onGenreSelect", "onGenreSelect: genre: $genre")
            tryToExecute(
                function = {
                    val x = strategy.getMoviesBasedOnCategory(genre.id, page = 1)
                    Log.d("onGenreSelect", "onGenreSelect: movie genres : $x")
                    x
                },
                onSuccess = { movies ->
                    updateState { it.copy(filteredMovies = movies.map { movie -> movie.toUiState() }) }
                },
                onError = { /* Handle if needed */ }
            )
        }
    }

    override fun onMovieClick(movieId: Int) {
        emitNewEffect(SeeAllEffect.NavigateToMovieDetails(movieId))
    }

    override fun onBackClick() {
        emitNewEffect(effect = SeeAllEffect.OnNavigateBack)
    }

    override fun onClickAllChip() {
        loadAllMovies()
    }
}

fun Movie.toUiState(): MoviesUiState {
    return MoviesUiState(
        id = this.id.toString(),
        imageUrl = imageUrl,
        rate = this.rate.toString(),
        name = this.title,
        genre = this.genre.map { it.toCategoryUiState() },
    )
}

