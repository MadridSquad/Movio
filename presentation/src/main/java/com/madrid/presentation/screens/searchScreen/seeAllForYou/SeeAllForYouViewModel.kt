package com.madrid.presentation.screens.searchScreen.seeAllForYou

import androidx.paging.map
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.presentation.pagination.ForYouPagingSource
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.uiStateMapper.toMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SeeAllForYouViewModel @Inject constructor(
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase
) : BaseViewModel<SeeAllForYouUIState, Nothing>(
    SeeAllForYouUIState()
) {

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        launchPagingRequest(
            pagingSourceFactory = { ForYouPagingSource(getRecommendedMovieUseCase) },
            onStartLoading = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = { pagingDataFlow ->
                updateState { currentState ->
                    currentState.copy(
                        isLoading = false,
                        forYouMovies = pagingDataFlow.map { pagingData ->
                            pagingData.map { movie -> movie.toMovieUiState() }
                        }
                    )
                }
            },
            onError = { error ->
                updateState { it.copy(isLoading = false) }

            }
        )
    }
}