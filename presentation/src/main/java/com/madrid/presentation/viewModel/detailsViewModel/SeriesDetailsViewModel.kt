package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.series.AddRatingSeriesUseCase
import com.madrid.domain.usecase.series.AddSeriesToHistoryUseCase
import com.madrid.domain.usecase.series.GetEpisodeTrailersUseCase
import com.madrid.domain.usecase.series.GetEpisodesForSeasonUseCase
import com.madrid.domain.usecase.series.GetSeriesDetailsUseCase
import com.madrid.domain.usecase.series.GetSeriesReviewsUseCase
import com.madrid.domain.usecase.series.GetSeriesTopCastUseCase
import com.madrid.domain.usecase.series.GetSeriesTrailersUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import com.madrid.domain.usecase.series.IsFavoriteSeriesUseCase
import com.madrid.domain.usecase.series.SetSeriesFavoriteStatusUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.formatDuration
import com.madrid.presentation.viewModel.shared.parser.formatDateKotlinx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    private val getSeriesTopCastUseCase: GetSeriesTopCastUseCase,
    private val getSeriesReviewsUseCase: GetSeriesReviewsUseCase,
    private val getSimilarSeriesUseCase: GetSimilarSeriesUseCase,
    private val getEpisodesForSeasonUseCase: GetEpisodesForSeasonUseCase,
    private val addSeriesToHistoryUseCase: AddSeriesToHistoryUseCase,
    private val addRatingSeriesUseCase: AddRatingSeriesUseCase,
    private val isGuestUseCase: LoginUseCase,
    private val getSeriesTrailersUseCase: GetSeriesTrailersUseCase,
    private val setSeriesFavoriteStatusUseCase: SetSeriesFavoriteStatusUseCase,
    private val isFavoriteSeriesUseCase: IsFavoriteSeriesUseCase,
    private val getEpisodeTrailersUseCase: GetEpisodeTrailersUseCase,
) : BaseViewModel<SeriesDetailsUiState, Nothing>(SeriesDetailsUiState()) {
    private val args = savedStateHandle.toRoute<Destinations.SeriesDetailsScreen>()

    init {
        fetchIsGuest()
        saveSeriesToHistory()
        loadData()
        checkIfFavoriteSeriesUseCase()
    }

    private fun saveSeriesToHistory() {
        tryToExecute(
            function = { addSeriesToHistoryUseCase(args.seriesId) },
            onSuccess = {},
            onError = {}
        )
    }

    private fun loadTrailer() {
        tryToExecute(
            function = { getSeriesTrailersUseCase(args.seriesId) },
            onSuccess = { trailers ->
                val trailerKey = trailers.firstOrNull()?.key // or filter official
                if (trailerKey != null) {
                    updateState { it.copy(trailerKey = trailerKey) }
                }
            },
            onError = { error ->
                Log.d("SeriesTrailer", "Failed to load trailer: ${error.message}")
            }
        )
    }

    fun loadEpisodeTrailer(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        onTrailerLoaded: (String?) -> Unit
    ) {
        tryToExecute(
            function = { getEpisodeTrailersUseCase(seriesId, seasonNumber, episodeNumber) },
            onSuccess = { trailers ->
                val trailerKey = trailers.firstOrNull()?.key
                onTrailerLoaded(trailerKey)
            },
            onError = {
                onTrailerLoaded(null)
            }
        )
    }

    private fun loadData() {
        tryToExecute(
            function = { getSeriesDetailsUseCase(args.seriesId) },
            onSuccess = { series ->
                updateState {
                    it.copy(
                        seriesId = series.id,
                        isLoading = false,
                        topImageUrl = series.imageUrl,
                        seriesName = series.title,
                        rate = RateFormatter.formatRate(series.rate), // Format rate here
                        numberOfSeasons = series.seasons.size,
                        productionDate = formatDateKotlinx(series.airDate),
                        description = formatDuration(series.description),
                        currentSeasonsUiStates = series.seasons.map { season -> season.mapToUiState() },
                        selectedSeasonUiState = series.seasons[if (series.seasons.first().seasonNumber == 0) args.seasonNumber else args.seasonNumber - 1].mapToUiState()
                    )
                }
                loadAllSeasonsEpisodes()
                loadCastData()
                loadReviews()
                loadSimilarSeries()
                loadTrailer()
                loadSeasonEpisodes(if (series.seasons.first().seasonNumber == 0) args.seasonNumber else args.seasonNumber)
            },
            onError = {
                updateState { it.copy(isLoading = true) }
            },
        )
    }

    private fun loadAllSeasonsEpisodes() {
        viewModelScope.launch {
            state.first().currentSeasonsUiStates.forEachIndexed { index, season ->
                tryToExecute(
                    function = {
                        getEpisodesForSeasonUseCase(args.seriesId, season.seasonNumber)
                    },
                    onSuccess = { episodes ->
                        updateState { currentState ->
                            currentState.copy(
                                currentSeasonsUiStates = currentState.currentSeasonsUiStates.mapIndexed { seasonIndex, currentSeason ->
                                    if (season.seasonNumber == currentSeason.seasonNumber)
                                        currentSeason.copy(
                                            numberOfEpisodes = episodes.size,
                                            episodesUiStates = episodes.map { episode -> episode.toUiState() })
                                    else
                                        currentSeason
                                }
                            )
                        }
                    },
                    onError = {
                        updateState { it.copy(isLoading = true) }
                    },
                )
            }
        }
    }

    fun updateSelectedSeason(seasonNumber: Int) = loadSeasonEpisodes(seasonNumber)

    private fun loadSeasonEpisodes(seasonNumber: Int = 1) {
        tryToExecute(
            function = {
                getEpisodesForSeasonUseCase(args.seriesId, seasonNumber)
            },
            onSuccess = { episodes ->
                updateState { state ->
                    state.copy(
                        selectedSeasonUiState = state.selectedSeasonUiState.copy(
                            episodesUiStates = episodes.map { episode ->
                                episode.toUiState()
                            },
                            numberOfEpisodes = episodes.size,
                            seasonNumber = seasonNumber,
                            imageUrl = state.currentSeasonsUiStates[if (state.currentSeasonsUiStates.first().seasonNumber == 0) seasonNumber else seasonNumber - 1].imageUrl
                        )
                    )
                }
            },
            onError = {
                updateState { it.copy(isLoading = true) }
            },
        )
    }

    private fun loadCastData() {
        tryToExecute(
            function = {
                getSeriesTopCastUseCase(args.seriesId)
            },
            onSuccess = { artists ->
                updateState {
                    it.copy(topCast = artists.map { artist ->
                        artist.mapToUiState()
                    })
                }
            },
            onError = { e ->
                Log.d("TAG lol", "loadCastData: ${e.message}")
            },
        )
    }

    private fun loadReviews() {
        tryToExecute(
            function = {
                getSeriesReviewsUseCase(args.seriesId)
            },
            onSuccess = { reviews ->
                updateState {
                    it.copy(reviews = reviews.map { review ->
                        review.toUiState()
                    })
                }
            },
            onError = { e ->
                Log.d("TAG lol", "loadCastData: ${e.message}")
                updateState { it.copy(isLoading = true) }
            },
        )
    }

    private fun loadSimilarSeries() {
        tryToExecute(
            function = {
                getSimilarSeriesUseCase(args.seriesId)
            },
            onSuccess = { allSeries ->
                updateState {
                    it.copy(similarSeries = allSeries.map { series ->
                        series.toUiState()
                    })
                }
            },
            onError = { e ->
                Log.d("TAG lol", "loadCastData: ${e.message}")
                updateState { it.copy(isLoading = true) }
            },
        )
    }

    private fun fetchIsGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isGuestUseCase.isGuest().collectLatest { result ->
                    updateState { it.copy(isGuest = result) }
                }
            } catch (e: Exception) {
                updateState { it.copy(isGuest = true) }
            }
        }
    }

    fun onPickRatingNumber(rating: Int) {
        updateState {
            it.copy(
                userRating = rating
            )
        }
    }

    fun addRating() {
        tryToExecute(
            function = {
                addRatingSeriesUseCase(
                    state.value.seriesId,
                    state.value.userRating.toDouble() * 2
                )
            },
            onSuccess = {},
            onError = {},
        )
    }

    fun onClickFavoriteIcon(seriesId: Int) {
        tryToExecute(
            function = { setSeriesFavoriteStatusUseCase(seriesId, state.value.isFavourite.not()) },
            onSuccess = {
                updateState {
                    it.copy(isFavourite = state.value.isFavourite.not())
                }
            },
            onError = {},
        )
    }

    private fun checkIfFavoriteSeriesUseCase() {
        tryToExecute(
            function = { isFavoriteSeriesUseCase(args.seriesId) },
            onSuccess = { isFavorite ->
                Log.d("checkIfFavoriteSeriesUseCase", "checkIfFavoriteSeriesUseCase: $isFavorite")
                updateState {
                    it.copy(isFavourite = isFavorite)
                }
            },
            onError = {
                updateState { state ->
                    state.copy(isFavourite = false)
                }
            },
        )
    }
}

fun Series.toUiState(): SeriesUiState {
    return SeriesUiState(
        id = this.id,
        name = this.title,
        imageUrl = this.imageUrl,
        rate = RateFormatter.formatRate(this.rate)
    )
}


fun Review.toUiState(): ReviewUiState {
    return ReviewUiState(
        reviewerName = this.reviewerName,
        reviewerImageUrl = this.reviewerPhotoUrl,
        rating = this.rate.toFloat(),
        date = (this.date),
        content = this.comment
    )
}