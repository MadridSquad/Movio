package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.SimilarSeries
import com.madrid.domain.usecase.mediaDeatailsUseCase.SeriesDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.text.toInt

class SeriesDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val seriesDetailsUseCase: SeriesDetailsUseCase
) : BaseViewModel<SeriesDetailsUiState, Nothing>(SeriesDetailsUiState()) {
    private val args = savedStateHandle.toRoute<Destinations.SeriesDetailsScreen>()

    init {
        Log.d("loool", ": ")
        loadData()
    }

    private fun loadData() {
        tryToExecute(
            function = { seriesDetailsUseCase.getSeriesDetailsById(args.seriesId.toInt()) },
            onSuccess = { series ->
                updateState {
                    it.copy(
                        seriesId = series.id,
                        topImageUrl = series.imageUrl,
                        seriesName = series.title,
                        rate = series.rate.toString(),
                        numberOfSeasons = series.seasons.size,
                        productionDate = series.yearOfRelease,
                        description = series.description,
                        currentSeasonsUiStates = series.seasons.map { season -> season.mapToUiState() },
                        selectedSeasonUiState = series.seasons[if (series.seasons.first().seasonNumber == 0) args.seasonNumber  else args.seasonNumber -1].mapToUiState()
                    )
                }
                loadAllSeasonsEpisodes()
                loadCastData()
                loadReviews()
                loadSimilarSeries()
                loadSeasonEpisodes(if (series.seasons.first().seasonNumber == 0) args.seasonNumber  else args.seasonNumber)
            },
            onError = {},
        )
    }

    private fun loadAllSeasonsEpisodes() {
        viewModelScope.launch {
            val seasonCount = state.first().currentSeasonsUiStates.size
            Log.d("TAG lol", "loadAllSeasonsEpisodes: ${state.first().numberOfSeasons}")
            state.first().currentSeasonsUiStates.forEachIndexed { index, season ->
                tryToExecute(
                    function = {
                        seriesDetailsUseCase.getEpisodesBySeriesId(
                            args.seriesId,
                            season.seasonNumber
                        )
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
                    onError = { },
                )
            }
        }
    }

    fun updateSelectedSeason(seasonNumber: Int) = loadSeasonEpisodes(seasonNumber)

    private fun loadSeasonEpisodes(seasonNumber: Int = 1) {
        tryToExecute(
            function = {
                seriesDetailsUseCase.getEpisodesBySeriesId(
                    args.seriesId.toInt(),
                    seasonNumber
                )
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
                            imageUrl = state.currentSeasonsUiStates[if (state.currentSeasonsUiStates.first().seasonNumber == 0) seasonNumber else seasonNumber -1].imageUrl
                        )
                    )
                }
            },
            onError = { },
        )
    }

    private fun loadCastData() {
        tryToExecute(
            function = {
                seriesDetailsUseCase.getSeriesCreditsById(args.seriesId.toInt())
            },
            onSuccess = { Artists ->
                updateState {
                    it.copy(topCast = Artists.map { artist ->
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
                seriesDetailsUseCase.getSeriesReviewsById(args.seriesId.toInt())
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
            },
        )
    }

    private fun loadSimilarSeries() {
        tryToExecute(
            function = {
                seriesDetailsUseCase.getSimilarSeriesById(args.seriesId.toInt())
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
            },
        )
    }
}

fun SimilarSeries.toUiState(): SeriesUiState {
    return SeriesUiState(
        id = this.id,
        name = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate.toString()
    )
}


fun Review.toUiState(): ReviewUiState {
    return ReviewUiState(
        reviewerName = this.reviewerName,
        reviewerImageUrl = "",
        rating = this.rate.toFloat(),
        date = this.dateOfRelease,
        content = this.comment
    )
}

