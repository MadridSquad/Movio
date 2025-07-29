package com.madrid.presentation.viewModel.seeAll

import android.util.Log
import com.madrid.domain.usecase.generUseCase.GetSeriesGenresUseCase
import com.madrid.domain.usecase.homeUseCase.SeriesByGenresUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel

class SeeAllTVShowsViewModel(
    private val seeAllTopRatingTVShows: SeeAllTopRatingTVShows,
    private val seriesByGenresUseCase: SeriesByGenresUseCase,
    private val getGenresUseCase: GetSeriesGenresUseCase,
    private val seeAllTVShowsStrategy: SeeAllTVShowsStrategy,
    private val seeAllOnTvShow: SeeAllOnTvShow,
    private val seeAllAiringTodayTvShow: SeeAllAiringTodayTvShow,
    private val seeAllRecommendedTVShow: SeeAllRecommendedTVShow
) : BaseViewModel<SeeAllTVShowsUiState, SeeAllEffect>(
    SeeAllTVShowsUiState()
), SeeAllTVShowsInteractionListener {

    init {
        loadGenres()
        onTitleUpdate()
    }

    override fun onSeriesClick(seriesId: Int) {
        emitNewEffect(SeeAllEffect.NavigateToSeriesDetails(seriesId))
    }

    override fun onGenreSelect(genre: String) {
        tryToExecute(
            function = { seriesByGenresUseCase(genre = genre) },
            onSuccess = { result ->
                val seriesList = result[genre] ?: emptyList()
                val filtered = seriesList.map { series ->
                    SeriesUiState(
                        id = series.id.toString(),
                        name = series.title,
                        rate = series.rate.toString(),
                        imageUrl = series.imageUrl,
                        genre = listOf(genre)
                    )
                }
                updateState {
                    it.copy(
                        selectedGenre = genre,
                        filteredSeries = filtered
                    )
                }
            }, onError = {})
    }

    override fun onBackClick() {
        emitNewEffect(effect = SeeAllEffect.OnNavigateBack)
    }

    private fun onTitleUpdate() {
        updateState { it.copy(title = seeAllTVShowsStrategy.getTitle()) }
    }

    private fun loadGenres() {
        tryToExecute(
            function = { getGenresUseCase() },
            onSuccess = { genres ->
                updateState { it.copy(genre = genres) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    fun loadTopRatedSeries() {
        tryToExecute(
            function = {
                seeAllTopRatingTVShows.getTVShows(6)
            },
            onSuccess = { result ->
                val uiTVShows = result.map { TVShows ->
                    SeriesUiState(
                        id = TVShows.id.toString(),
                        imageUrl = TVShows.imageUrl,
                        rate = TVShows.rate.toString(),
                        name = TVShows.title,
                        genre = TVShows.genre
                    )
                }
                updateState {
                    Log.d("log items", "TopRatingScreen: $uiTVShows")
                    it.copy(filteredSeries = uiTVShows)
                }
            },
            onError = { /* Handle if needed */ }
        )
    }

    fun loadAllOnTvShow() {
        tryToExecute(
            function = {
                seeAllOnTvShow.getTVShows(6)
            },
            onSuccess = { result ->
                val uiTVShows = result.map { TVShows ->
                    SeriesUiState(
                        id = TVShows.id.toString(),
                        imageUrl = TVShows.imageUrl,
                        rate = TVShows.rate.toString(),
                        name = TVShows.title,
                        genre = TVShows.genre
                    )
                }
                updateState {
                    Log.d("log items", "TopRatingScreen: $uiTVShows")
                    it.copy(filteredSeries = uiTVShows)
                }
            },
            onError = { /* Handle if needed */ }
        )
    }

    fun loadAiringTodayTvShow() {
        tryToExecute(
            function = {
                seeAllAiringTodayTvShow.getTVShows(6)
            },
            onSuccess = { result ->
                val uiTVShows = result.map { TVShows ->
                    SeriesUiState(
                        id = TVShows.id.toString(),
                        imageUrl = TVShows.imageUrl,
                        rate = TVShows.rate.toString(),
                        name = TVShows.title,
                        genre = TVShows.genre
                    )
                }
                updateState {
                    Log.d("log items", "TopRatingScreen: $uiTVShows")
                    it.copy(filteredSeries = uiTVShows)
                }
            },
            onError = { /* Handle if needed */ }
        )
    }

    fun loadRecommendedTVShow() {
        tryToExecute(
            function = {
                seeAllRecommendedTVShow.getTVShows(6)
            },
            onSuccess = { result ->
                val uiTVShows = result.map { TVShows ->
                    SeriesUiState(
                        id = TVShows.id.toString(),
                        imageUrl = TVShows.imageUrl,
                        rate = TVShows.rate.toString(),
                        name = TVShows.title,
                        genre = TVShows.genre
                    )
                }
                updateState {
                    Log.d("log items", "TopRatingScreen: $uiTVShows")
                    it.copy(filteredSeries = uiTVShows)
                }
            },
            onError = { /* Handle if needed */ }
        )
    }
}