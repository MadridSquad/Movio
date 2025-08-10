package com.madrid.presentation.viewModel.seeAll.tvShows

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.GetSeriesDetailsUseCase
import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
import com.madrid.presentation.pagination.SeeAllSeriesPagingSource
import com.madrid.presentation.pagination.SeeAllSeriesWithGenrePagingSource
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@HiltViewModel(assistedFactory = SeeAllTVShowsViewModel.Factory::class)
class SeeAllTVShowsViewModel @AssistedInject constructor(
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    @Assisted private val strategy: SeeAllTVShowsStrategy,
) : BaseViewModel<SeeAllTVShowsUiState, SeeAllEffect>(SeeAllTVShowsUiState()),
    SeeAllTVShowsInteractionListener {


    @AssistedFactory
    interface Factory {
        fun create(
            strategy: SeeAllTVShowsStrategy,
        ): SeeAllTVShowsViewModel
    }
    init {
        Log.d("TAG zoz", "in view model init")
        loadTitle()
        loadGenres()
        loadAllSeries()
    }

    private fun loadTitle() {
        updateState { it.copy(title = strategy.getTitle()) }
    }

    fun <T : Any> launchPagingRequest(
        pagingSourceFactory: () -> PagingSource<Int, T>,
        onSuccess: (Flow<PagingData<T>>) -> Unit,
        config: PagingConfig = PagingConfig(pageSize = 20),
    ) {
        try {
            updateState {
                it.copy(
                    isLoading = true,
                )
            }
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

    private fun loadGenres() {
        tryToExecute(
            function = { getSeriesGenresUseCase() },
            onSuccess = { genres ->
                Log.d("TAG zoz", "in view model init")
                updateState { it.copy(genre = genres.map { genre -> CategoryUiState(genre.id, genre.name) }) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    private fun loadAllSeries() {
        launchPagingRequest(
            pagingSourceFactory = {
                SeeAllSeriesPagingSource(strategy::getAllTvShows)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { series ->
                        SeriesUiState(
                            id = series.id.toString(),
                            imageUrl = series.imageUrl,
                            rate = RateFormatter.formatRate(series.rate),
                            name = series.title,
                        )
                    }
                }

                updateState {
                    it.copy(
                        filteredSeries = result,
                        isLoading = false
                    )
                }

            }
        )
    }


    override fun onGenreSelect(genre: CategoryUiState?) {
        if (genre == null) {
            loadAllSeries()

        } else {
            launchPagingRequest(
                pagingSourceFactory = {
                    SeeAllSeriesWithGenrePagingSource(
                        genreId = genre!!.id,
                        strategy::getTvShowsBasedOnCategory
                    )
                },
                onSuccess = { pagingFlow ->
                    val result = pagingFlow.map { pagingData ->
                        pagingData.map { series ->
                            SeriesUiState(
                                id = series.id.toString(),
                                imageUrl = series.imageUrl,
                                rate = RateFormatter.formatRate(series.rate),
                                name = series.title,
                            )
                        }
                    }

                    updateState {
                        it.copy(
                            filteredSeries = result,
                            isLoading = false
                        )
                    }

                }
            )
        }


    }

    override fun onSeriesClick(seriesId: Int) {
        emitNewEffect(SeeAllEffect.NavigateToSeriesDetails(seriesId))
    }

    override fun onBackClick() {
        emitNewEffect(effect = SeeAllEffect.OnNavigateBack)
    }

    override fun onClickAllChip() {
        loadAllSeries()
    }
}

fun Series.toUiState(): SeriesUiState {
    return SeriesUiState(
        id = this.id.toString(),
        imageUrl = imageUrl,
        rate = this.rate.toString(),
        name = this.title,
        genre = this.genre.map {
            CategoryUiState(
                id = it.id,
                name = it.name
            )
        }
    )
}