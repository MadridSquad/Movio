package com.madrid.presentation.viewModel.seeAll.tvShows

import android.util.Log
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.GetSeriesDetailsUseCase
import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel

class SeeAllTVShowsViewModel(
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    private val strategy: SeeAllTVShowsStrategy,
) : BaseViewModel<SeeAllTVShowsUiState, SeeAllEffect>(SeeAllTVShowsUiState()),
    SeeAllTVShowsInteractionListener {

    init {
        Log.d("TAG zoz", "in view model init")
        loadTitle()
        loadGenres()
        loadAllSeries()
    }

    private fun loadTitle() {
        updateState { it.copy(title = strategy.getTitle()) }
    }

    private fun loadGenres() {
        tryToExecute(
            function = { getSeriesGenresUseCase() },
            onSuccess = { genres ->
                Log.d("TAG zoz", "in view model init")
                updateState { it.copy(genre = genres.map { genre -> genre.toCategoryUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    private fun loadAllSeries() {
        tryToExecute(
            function = { strategy.getAllTvShows(1) },
            onSuccess = { allSeries ->
                updateState { it.copy(filteredSeries = allSeries.map { series -> series.toUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }


    override fun onGenreSelect(genre: CategoryUiState?) {
        if (genre == null)
            loadAllSeries()
        else {
            Log.d("onGenreSelect", "onGenreSelect: genre: $genre")
            tryToExecute(
                function = {
                    val x = strategy.getTvShowsBasedOnCategory(genre.id)
                    Log.d("onGenreSelect", "onGenreSelect: series genres : $x")
                    x
                },
                onSuccess = { allSeries ->
                    updateState { it.copy(filteredSeries = allSeries.map { series -> series.toUiState() }) }
                },
                onError = { /* Handle if needed */ }
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
        genre = this.genre.map { it.toCategoryUiState() },
    )
}