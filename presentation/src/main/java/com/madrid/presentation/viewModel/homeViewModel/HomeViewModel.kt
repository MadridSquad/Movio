package com.madrid.presentation.viewModel.homeViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.movie.GetTrendingMoviesUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : BaseViewModel<TrendingUiState, Nothing>(
    TrendingUiState()
) {

    init {
        fetchTrending()
    }

    internal fun fetchTrending() {
        updateState { it.copy(isLoading = true, errorMessage = "") }
        tryToExecute(
            function = {
                getTrendingMoviesUseCase(page = 1)
            },
            onSuccess = { trendingItems ->
                updateState {
                    it.copy(
                        isLoading = false,
                        trending = trendingItems.map { item ->
                            Trending(
                                id = item.id,
                                title = item.title,
                                posterPath = item.imageUrl,
                                voteAverage = item.rate,
                                mediaType = ""
                            )
                        },
                        errorMessage = ""
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "An error occurred"
                    )
                }
            },
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }
}