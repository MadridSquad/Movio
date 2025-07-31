package com.madrid.presentation.viewModel.homeViewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.madrid.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.toMediaUiState
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
) : BaseViewModel<HomeScreenState, HomeScreenEffect>(
    HomeScreenState()
), HomeInteractionListener {
    init {
        loadGenres()

    }

    fun loadGenres() {
        tryToExecute(
            function = ::getMixedGenres,
            onSuccess = { genres ->
                updateState {
                    it.copy(
                        categoryTabUiState = it.categoryTabUiState.copy(
                            categories = genres
                        )
                    )
                }
            },
            onError = { onError() },
        )
    }

    private suspend fun getMixedGenres(): List<CategoryUiState> {
        val moviesGenres = getMovieGenresUseCase()
        val seriesGenres = getSeriesGenresUseCase()
        val genres = (moviesGenres + seriesGenres).distinctBy { it.id }
        return genres.map { it.toCategoryUiState() }
    }

    private fun fetchMediaByCategory(genreId: Int, sortingType: SortingType) {
        startLoading()
        val result = Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = {
                HomeCategoriesPagingSource(
                    getMoviesByGenreIdUseCase = getMoviesByGenreIdUseCase,
                    getSeriesByGenreIdUseCase = getSeriesByGenreIdUseCase,
                    genreId = genreId,
                    sortBy = sortingType.toSortType()
                )
            }
        ).flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.flatMap { mixedData ->
                    val movieItems = mixedData.movies.map { it.toMediaUiState() }
                    val seriesItems = mixedData.series.map { it.toMediaUiState() }
                    movieItems + seriesItems
                }
            }
        updateState {
            it.copy(

                categoryTabUiState = it.categoryTabUiState.copy(
                    media = result
                )
            )
        }
    }

    override fun onSelectTab() {
        TODO("Not yet implemented")
    }

    override fun onSelectCategory(category: CategoryUiState) {
        updateState {
            it.copy(
                categoryTabUiState = it.categoryTabUiState.copy(
                    selectedCategory = category
                )
            )
        }
        fetchMediaByCategory(
            state.value.categoryTabUiState.selectedCategory.id,
            state.value.categoryTabUiState.sortingType
        )
    }

    override fun onSelectSortingType(sortType: SortingType) {
        updateState {
            it.copy(
                categoryTabUiState = it.categoryTabUiState.copy(
                    sortingType = sortType
                )
            )
        }
        val id = state.value.categoryTabUiState.selectedCategory.id
        fetchMediaByCategory(id, sortType)
    }

    override fun onMediaSelected(mediaId: Int) {
        emitNewEffect(HomeScreenEffect.NavigateToMediaDetails(mediaId))
    }

    private fun startLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onError(errorMessage: String = "") {
        updateState { it.copy(isLoading = false, errorMessage = errorMessage) }
    }
}