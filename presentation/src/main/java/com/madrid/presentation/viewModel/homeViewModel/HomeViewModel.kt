package com.madrid.presentation.viewModel.homeViewModel

import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.homeUseCase.GetAllTrendingUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getAllTrendingUseCase: GetAllTrendingUseCase
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
                getAllTrendingUseCase(page = 1)
            },
            onSuccess = { trendingItems ->
                updateState {
                    it.copy(
                        isLoading = false,
                        trending = trendingItems.map { item ->
                            Trending(
                                id = item.id,
                                title = item.title,
                                posterPath = item.posterPath,
                                voteAverage = item.voteAverage,
                                mediaType = item.mediaType
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