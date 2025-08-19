package com.madrid.presentation.viewModel.seeAll.movies

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.presentation.pagination.SeeAllMoviesPagingSource
import com.madrid.presentation.pagination.SeeAllMoviesWithGenrePagingSource
import com.madrid.presentation.utils.formatRate
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
                updateState { it.copy(genre = genres.map { genre -> genre.toCategoryUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    private fun loadAllMovies() {
        launchPagingRequest(
            pagingSourceFactory = {
                SeeAllMoviesPagingSource(
                    getAllMovie = strategy::getAllMovies
                )
            },
            onSuccess = { moviesFlow ->
                val mappedFlow = moviesFlow.map { pagingData ->
                    pagingData.map { movie -> movie.toUiState() }
                }
                updateState { it.copy(filteredMovies = mappedFlow, isLoading = false) }
            },
        )
    }

    override fun onGenreSelect(genre: CategoryUiState?) {
        if (genre == null)
            loadAllMovies()
        else {
            launchPagingRequest(
                pagingSourceFactory = {
                    SeeAllMoviesWithGenrePagingSource(
                        genreId =  genre.id,
                        getAllMovie = strategy::getMoviesBasedOnCategory
                    )
                },
                onSuccess = { pagingFlow ->
                    val result = pagingFlow.map { pagingData ->
                        pagingData.map { movie ->
                            MoviesUiState(
                                id = movie.id.toString(),
                                imageUrl = movie.imageUrl,
                                rate = formatRate(movie.rate),
                                name = movie.title,
                                genre = movie.genre.map { it.toCategoryUiState() },
                            )
                        }
                    }

                    updateState {
                        it.copy(
                            filteredMovies = result,
                            isLoading = false
                        )
                    }

                }
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

    fun <T : Any> launchPagingRequest(
        pagingSourceFactory: () -> PagingSource<Int, T>,
        onSuccess: (Flow<PagingData<T>>) -> Unit,
        config: PagingConfig = PagingConfig(pageSize = 20),
    ) {
        try {
            updateState { it.copy(isLoading = true) }
            val result = Pager(
                config = config,
                pagingSourceFactory = pagingSourceFactory
            ).flow.cachedIn(viewModelScope)

            onSuccess(result)

        } catch (e: Exception) {
            updateState {
                it.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
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

