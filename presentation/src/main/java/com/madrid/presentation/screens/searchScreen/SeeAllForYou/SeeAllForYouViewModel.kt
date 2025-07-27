package com.madrid.presentation.screens.searchScreen.SeeAllForYou

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.madrid.domain.usecase.GetRecommendedMovieUseCase
import com.madrid.presentation.screens.searchScreen.paging.ForYouPagingSource
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState
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
                pagingData.map { movie ->
                    SearchScreenState.MovieUiState(
                        id = movie.id.toString(),
                        title = movie.title,
                        imageUrl = movie.imageUrl,
                        rating = movie.rate.toString()
                    )
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