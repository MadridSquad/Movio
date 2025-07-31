package com.madrid.presentation.screens.searchScreen.SeeAllForYou

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.presentation.screens.searchScreen.paging.ForYouPagingSource
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.uiStateMapper.toMovieUiState
import kotlinx.coroutines.flow.map

class SeeAllForYouViewModel(
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase
) : BaseViewModel<SeeAllForYouUIState, Nothing>(
    SeeAllForYouUIState()
) {

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        updateState {
            it.copy(
                isLoading = true,
            )
        }

        val result = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                ForYouPagingSource(getRecommendedMovieUseCase)
            }
        ).flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.flatMap { movie ->
                    movie.map { it.toMovieUiState() }
                }
            }

        updateState { current ->
            current.copy(
                isLoading = false,
                forYouMovies = result,
            )
        }

    }
}