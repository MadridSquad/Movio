package com.madrid.presentation.viewModel.detailsViewModel.seriesDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.Trailer
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
import com.madrid.presentation.R
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.detailsViewModel.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.review.ReviewUiState
import com.madrid.presentation.viewModel.detailsViewModel.review.toReviewUiState
import com.madrid.presentation.viewModel.shared.parser.formatDateKotlinx
import com.madrid.presentation.viewModel.shared.parser.formatRate
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val loginUseCase: LoginUseCase,
    private val getSeriesTrailersUseCase: GetSeriesTrailersUseCase,
    private val setSeriesFavoriteStatusUseCase: SetSeriesFavoriteStatusUseCase,
    private val isFavoriteSeriesUseCase: IsFavoriteSeriesUseCase,
    private val getEpisodeTrailersUseCase: GetEpisodeTrailersUseCase,
) : BaseViewModel<SeriesDetailsUiState, SeriesDetailsEffect>(SeriesDetailsUiState()),
    SeriesDetailsInteractionListener {
    private val args = savedStateHandle.toRoute<Destinations.SeriesDetailsScreen>()

    init {
        loadData()
        saveSeriesToHistory()
        checkIfFavoriteSeries()
        isGuest()
        loadAllSeasonsEpisodes()
        updateSelectedSeason(args.seasonNumber)
    }

    private fun loadData() {
        loadSeries()
        loadCastData()
        loadReviews()
        loadSimilarSeries()
    }

    private fun loadSeries() {
        updateState { it.copy(showLoadingScreen = true) }
        tryToExecute(
            function = { getSeriesDetailsUseCase.invoke(args.seriesId) },
            onSuccess = { series -> ::onSuccessLoadSeries.invoke(series) },
            onError = { ::onError.invoke() }
        )
    }

    private fun loadAllSeasonsEpisodes() {
        updateState { it.copy(showLoadingScreen = true) }
        state.value.currentSeasonsUiStates.forEachIndexed { index, season ->
            tryToExecute(
                function = {
                    getEpisodesForSeasonUseCase.invoke(args.seriesId, season.seasonNumber)
                },
                onSuccess = { episodes ->
                    onSuccessLoadAllSeasonsEpisodes(episodes, season.seasonNumber)
                    updateState { it.copy(showLoadingScreen = false) }
                },
                onError = { error ->
                    onError()
                }
            )
        }
    }

    private fun loadCastData() {
        tryToExecute(
            function = { getSeriesTopCastUseCase.invoke(args.seriesId) },
            onSuccess = { artists -> ::onSuccessLoadCast.invoke(artists) },
            onError = { ::onError.invoke() }
        )
    }

    private fun loadTrailer() {
        tryToExecute(
            function = { getSeriesTrailersUseCase.invoke(args.seriesId) },
            onSuccess = { trailers -> ::onSuccessLoadTrailer.invoke(trailers) },
            onError = { ::onError.invoke() }
        )
    }

    private fun saveSeriesToHistory() {
        tryToExecute(
            function = { addSeriesToHistoryUseCase.invoke(args.seriesId) },
            onSuccess = {},
            onError = {}
        )
    }

    private fun loadReviews() {
        tryToExecute(
            function = { getSeriesReviewsUseCase.invoke(args.seriesId) },
            onSuccess = { reviews -> ::onSuccessLoadReviews.invoke(reviews) },
            onError = { ::onError.invoke() },
        )
    }

    private fun onClickFavoriteIcon(seriesId: Int) {
        if (state.value.isGuest) {
            updateState { it.copy(isLoginBottomSheetVisible = true) }
            return
        }
        tryToExecute(
            function = {
                setSeriesFavoriteStatusUseCase.invoke(
                    seriesId,
                    state.value.isFavourite.not()
                )
            },
            onSuccess = { ::onSuccessLoadFavourite.invoke() },
            onError = {}
        )
    }

    private fun addRating() {
        viewModelScope.launch {
            addRatingSeriesUseCase(state.value.seriesId, state.value.userRating.toDouble() * 2)
        }
    }

    private fun checkIfFavoriteSeries() {
        tryToExecute(
            function = { isFavoriteSeriesUseCase.invoke(args.seriesId) },
            onSuccess = { isFavorite -> ::onSuccessCheckIfFavoriteSeries.invoke(isFavorite) },
            onError = { ::onErrorCheckIfFavoriteSeries.invoke() },
        )
    }

    private fun loadSimilarSeries() {
        tryToExecute(
            function = { getSimilarSeriesUseCase.invoke(args.seriesId) },
            onSuccess = { allSeries -> ::onSuccessLoadSimilarSeries.invoke(allSeries) },
            onError = { ::onError.invoke() },
        )
    }

    private fun loadSelectedSeasonEpisodes(seasonNumber: Int = 1) {
        updateState { it.copy(showLoadingScreen = true) }
        tryToExecute(
            function = { getEpisodesForSeasonUseCase.invoke(args.seriesId, seasonNumber) },
            onSuccess = { episodes ->
                ::onSuccessLoadSeasonEpisodes.invoke(
                    episodes,
                    seasonNumber
                )
                updateState { it.copy(showLoadingScreen = false) }
            },
            onError = { ::onError.invoke() },
        )
    }

    private fun isGuest() {
        tryToCollect(
            function = { loginUseCase.isGuest() },
            onNewValue = { result -> onCheckGuestNewValue(result) },
            onError = { ::onGuestCheckError.invoke() }
        )
    }

    private fun loadEpisodeTrailer(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        onTrailerLoaded: (String?) -> Unit
    ) {
        updateState { it.copy(showLoadingScreen = true) }
        tryToExecute(
            function = { getEpisodeTrailersUseCase.invoke(seriesId, seasonNumber, episodeNumber) },
            onSuccess = { trailers ->
                ::onSuccessLoadEpisodeTrailer.invoke(
                    trailers,
                    onTrailerLoaded
                )
                updateState { it.copy(showLoadingScreen = false) }
            },
            onError = { onTrailerLoaded(null) }
        )
    }

    fun updateSelectedSeason(seasonNumber: Int) = loadSelectedSeasonEpisodes(seasonNumber)

    override fun onBackButtonClick() {
        emitNewEffect(effect = SeriesDetailsEffect.NavigateBack)
    }

    override fun onRateButtonClick() {
        addRating()
    }

    override fun onFavoriteClick(seriesId: Int) {
        onClickFavoriteIcon(seriesId)
    }

    override fun onPlayItClick() {
        loadTrailer()
    }

    override fun onPickRatingNumber(rating: Int) {
        updateState {
            it.copy(userRating = rating)
        }
    }

    override fun onEpisodePlayItClick(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        onTrailerLoaded: (String?) -> Unit
    ) {
        loadEpisodeTrailer(
            seriesId = seriesId, seasonNumber = seasonNumber,
            episodeNumber = episodeNumber,
            onTrailerLoaded = onTrailerLoaded
        )
    }

    override fun onSeeAllClick(seriesId: Int, seeAllType: SeeAllType) {
        emitNewEffect(
            effect = SeriesDetailsEffect.NavigateToSeeAllScreen(
                seriesId = seriesId,
                seeAllType = seeAllType
            )
        )
    }

    override fun onActorCardClick(actorId: Int) {
        emitNewEffect(effect = SeriesDetailsEffect.NavigateToActorDetails(actorId))
    }

    override fun onSimilarSeriesCardClick(seriesId: Int) {
        emitNewEffect(effect = SeriesDetailsEffect.NavigateToSeriesDetails(seriesId = seriesId))
    }

    override fun onCurrentSeasonCardClick(seriesId: Int, seasonNumber: Int) {
        emitNewEffect(
            effect = SeriesDetailsEffect.NavigateToEpisodesScreen(
                seriesId = seriesId,
                seasonNumber = seasonNumber
            )
        )
    }

    override fun onRetryButtonClick() {
        loadData()
    }

    override fun onLoginButtonClick() {
        emitNewEffect(effect = SeriesDetailsEffect.NavigateToAuthenticationScreen)
    }

    override fun onShareIconClick() {
        updateState { it.copy(showSheet = true) }
    }

    override fun onDismissShareBottomSheetClick() {
        updateState { it.copy(showSheet = false) }
    }

    override fun onShowDoneRatingBottomSheetClick() {
        updateState { it.copy(showDoneRatingBottomSheet = true) }
    }

    override fun onDismissShowDoneRatingBottomSheetClick() {
        updateState { it.copy(showDoneRatingBottomSheet = false) }
    }

    override fun onShowAddRatingBottomSheetClick() {
        updateState { it.copy(showAddRatingBottomSheet = true) }
    }

    override fun onDismissAddRatingBottomSheet() {
        updateState { it.copy(showAddRatingBottomSheet = false) }
    }

    override fun onShowSnackBar() {
        updateState {
            it.copy(
                isError = false,
                errorResMessageId = R.string.feature_not_supported,
                showSnackBar = true
            )
        }
    }

    override fun onDismissSnackBar() {
        updateState {
            it.copy(isError = false, showSnackBar = false)
        }
    }

    override fun onDismissLoginBottomSheet() {
        updateState { it.copy(isLoginBottomSheetVisible = false) }
    }

    private fun onError() {
        updateState {
            it.copy(isError = true, showLoadingScreen = false)
        }
    }

    private fun onGuestCheckError() {
        updateState {
            it.copy(isGuest = true)
        }
    }

    private fun onErrorCheckIfFavoriteSeries() {
        updateState { state ->
            state.copy(isFavourite = false)
        }
    }

    private fun onSuccessLoadSeries(series: Series) {
        updateState {
            it.copy(
                seriesId = series.id,
                topImageUrl = series.imageUrl,
                seriesName = series.title,
                seriesGenre = series.genre.map { it.name },
                rate = formatRate(series.rate),
                numberOfSeasons = series.seasons.size,
                productionDate = formatDateKotlinx(series.airDate),
                description = series.description,
                currentSeasonsUiStates = series.seasons.map { season -> season.toUiState() },
                selectedSeasonUiState = series.seasons[if (series.seasons.first().seasonNumber == 0) args.seasonNumber else args.seasonNumber - 1].toUiState(),
                showLoadingScreen = false,
                isError = false,
            )
        }
        loadAllSeasonsEpisodes()
    }

    private fun onSuccessLoadAllSeasonsEpisodes(episode: List<Episode>, seasonNumber: Int) {
        updateState { currentState ->
            currentState.copy(
                currentSeasonsUiStates = currentState.currentSeasonsUiStates.map { currentSeason ->
                    if (currentSeason.seasonNumber == seasonNumber) {
                        currentSeason.copy(
                            numberOfEpisodes = episode.size,
                            episodesUiStates = episode.map { ep -> ep.toUiState() }
                        )
                    } else {
                        currentSeason
                    }
                },
                seeAllType = SeeAllType.Season
            )
        }
    }

    private fun onSuccessLoadCast(artists: List<Artist>) {
        updateState {
            it.copy(
                topCast = artists.map { artist -> artist.toUiState() },
                seeAllType = SeeAllType.TopCast
            )
        }
    }

    private fun onSuccessLoadReviews(domainReviews: List<Review>) {

        updateState { currentState -> currentState.copy(reviews = domainReviews.map { review ->
            review.toReviewUiState() })
        }

    }

    private fun onSuccessLoadTrailer(trailers: List<Trailer>) {
        val trailerKey = trailers.firstOrNull()?.key
        if (trailerKey != null) {
            updateState { it.copy(trailerKey = trailerKey) }
        }
    }

    private fun onSuccessLoadFavourite() {
        updateState {
            it.copy(isFavourite = state.value.isFavourite.not())
        }
    }

    private fun onSuccessCheckIfFavoriteSeries(isFavorite: Boolean) {
        updateState {
            it.copy(isFavourite = isFavorite)
        }
    }

    private fun onSuccessLoadSimilarSeries(allSeries: List<Series>) {
        updateState {
            it.copy(
                similarSeries = allSeries.map { series -> series.toUiState() },
                seeAllType = SeeAllType.SimilarSeries
            )
        }
    }

    private fun onSuccessLoadSeasonEpisodes(episodes: List<Episode>, seasonNumber: Int) {
        updateState { state ->
            state.copy(
                selectedSeasonUiState = state.selectedSeasonUiState.copy(
                    episodesUiStates = episodes.map { episode -> episode.toUiState() },
                    numberOfEpisodes = episodes.size,
                    seasonNumber = seasonNumber,
                    imageUrl = state.currentSeasonsUiStates[
                        if (
                            state.currentSeasonsUiStates.first().seasonNumber == 0) seasonNumber
                        else seasonNumber - 1].imageUrl
                )
            )
        }
    }

    private fun onSuccessLoadEpisodeTrailer(
        trailers: List<Trailer>,
        onTrailerLoaded: (String?) -> Unit
    ) {
        val trailerKey = trailers.firstOrNull()?.key
        onTrailerLoaded(trailerKey)
    }

    private fun onCheckGuestNewValue(result: Boolean) {
        updateState {
            it.copy(isGuest = result)
        }
    }
}