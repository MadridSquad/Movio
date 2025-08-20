package com.madrid.presentation.viewModel.homeViewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.madrid.domain.usecase.movie.GetMoviesWithTrailers
import com.madrid.domain.usecase.movie.GetNowPlayingMovieUseCase
import com.madrid.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.madrid.domain.usecase.movie.GetTrendingMoviesUseCase
import com.madrid.domain.usecase.movie.GetUpcomingMovieUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.series.GetAiringTodaySeriesUseCase
import com.madrid.domain.usecase.series.GetOnAirSeriesUseCase
import com.madrid.domain.usecase.series.GetRecommendedSeriesUseCase
import com.madrid.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
import com.madrid.domain.usecase.series.GetSeriesWithTrailersUseCase
import com.madrid.domain.usecase.series.GetTopRatedSeriesUseCase
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getRecommendedMoviesUseCase: GetRecommendedMovieUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMovieUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMovieUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val getOnAirSeriesUseCase: GetOnAirSeriesUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase,
    private val getMoviesWithTrailers: GetMoviesWithTrailers,
    private val getSeriesWithTrailers: GetSeriesWithTrailersUseCase,
) : BaseViewModel<HomeScreenState, HomeScreenEffect>(
    HomeScreenState()
), HomeInteractionListener {
    init {
        loadGenres()
        loadFileImage()
        loadMoviesLayoutData()
    }

    private fun loadFileImage() {
        tryToExecute(
            function = {
                getCurrentUserDetailsUseCase()
            },
            onSuccess = { user ->
                updateState {
                    it.copy(
                        profileImage = user?.profilePicUrl
                    )
                }
                fetchMediaByCategory(null, state.value.categoryTabUiState.sortingType)
            },
            onError = { onError() },
        )
    }

    private fun loadGenres() {
        tryToExecute(
            function = ::getMixedGenres,
            onSuccess = { genres ->
                updateState {
                    it.copy(
                        categoryTabUiState = it.categoryTabUiState.copy(
                            categories = genres,
                            selectedCategory = null
                        )
                    )
                }
                fetchMediaByCategory(null, state.value.categoryTabUiState.sortingType)
            },
            onError = { onError() },
        )
    }

    private suspend fun getMixedGenres(): List<CategoryUiState> {
        val moviesGenres = getMovieGenresUseCase()
        val seriesGenres = getSeriesGenresUseCase()
        val genres = (moviesGenres + seriesGenres).distinctBy { it.id }

        return genres.map { it.toCategoryUiState() }
    }

    private fun fetchMediaByCategory(genreId: Int?, sortingType: SortingType) {
        startCategoryMediaLoading()
        val result = Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = {
                HomeCategoriesPagingSource(
                    getMoviesByGenreIdUseCase = getMoviesByGenreIdUseCase,
                    getSeriesByGenreIdUseCase = getSeriesByGenreIdUseCase,
                    genreId = genreId,
                    sortBy = sortingType.toSortType()
                )
            }
        ).flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.flatMap { mixedData ->
                    val movieItems = mixedData.movies.map { it.toMediaUiState() }
                    val seriesItems = mixedData.series.map { it.toMediaUiState() }
                    movieItems + seriesItems
                }
            }
        updateState {
            it.copy(

                categoryTabUiState = it.categoryTabUiState.copy(
                    media = result,
                    isLoading = false
                )
            )
        }
    }

    override fun onSelectTab(index: Int) {
        updateState { it.copy(selectedTabIndex = index) }
        when (index) {
            0 -> loadMoviesLayoutData()
            1 -> loadSeriesLayoutData()
            2 -> {
                updateState { state ->
                    state.copy(
                        categoryTabUiState = CategoryTabUiState()
                    )
                }
                loadGenres()
            }
        }
    }

    override fun onSelectCategory(category: CategoryUiState) {
        updateState {
            it.copy(
                categoryTabUiState = it.categoryTabUiState.copy(
                    selectedCategory = category
                )
            )
        }
        val genreId = if (category.id == -1) null else category.id
        fetchMediaByCategory(genreId, state.value.categoryTabUiState.sortingType)
    }

    override fun onSelectSortingType(sortType: SortingType) {
        updateState {
            it.copy(
                categoryTabUiState = it.categoryTabUiState.copy(
                    sortingType = sortType
                )
            )
        }
        val selectedCategoryId = state.value.categoryTabUiState.selectedCategory?.id
        val genreId = if (selectedCategoryId == -1) null else selectedCategoryId
        fetchMediaByCategory(genreId, sortType)
    }

    override fun onMediaSelected(mediaId: Int, mediaType: MediaType) {
        emitNewEffect(HomeScreenEffect.NavigateToMediaDetails(mediaId, mediaType))
    }

    override fun onClickTryAgainButton() {
        val selectedCategoryId = state.value.categoryTabUiState.selectedCategory?.id
        val selectedSortingType = state.value.categoryTabUiState.sortingType
        if (selectedCategoryId == -1) {
            fetchMediaByCategory(null, selectedSortingType)
        } else {
            fetchMediaByCategory(selectedCategoryId, selectedSortingType)
        }
    }

    override fun onClickProfile() {
        emitNewEffect(HomeScreenEffect.NavigateToProfile)
    }

    private fun startCategoryMediaLoading() {
        updateState { homeScreenState ->
            homeScreenState.copy(
                categoryTabUiState = homeScreenState.categoryTabUiState.copy(
                    isLoading = true,
                )
            )
        }
    }

    private fun onError(errorMessage: String = "") {
        updateState { it.copy(isLoading = true, errorMessage = errorMessage) }
    }

    /*************************************** Movies Section ***************************************/

    private fun trendingSectionMoviesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                movieTabUiState = state.movieTabUiState.copy(
                    trending = state.movieTabUiState.trending.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun topRatingSectionMoviesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                movieTabUiState = state.movieTabUiState.copy(
                    topRated = state.movieTabUiState.topRated.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun nowPlayingSectionMoviesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                movieTabUiState = state.movieTabUiState.copy(
                    nowPlaying = state.movieTabUiState.nowPlaying.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun upcomingSectionMoviesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                movieTabUiState = state.movieTabUiState.copy(
                    upcoming = state.movieTabUiState.upcoming.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun recommendedSectionMoviesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                movieTabUiState = state.movieTabUiState.copy(
                    moreRecommended = state.movieTabUiState.moreRecommended.copy(isLoading = isLoading)
                )
            )
        }
    }

    override fun loadMoviesLayoutData() {
        loadSliderMovies()
        loadTopRatingMoviesSection()
        loadNowPlayingMoviesSection()
        loadUpComingMoviesSection()
        loadRecommendedMoviesSection()
    }

    private fun loadSliderMovies() {

        tryToExecute(
            function = {
                trendingSectionMoviesLoading(true)
                val trendingMovies = getTrendingMoviesUseCase(1)
                try {
                    getMoviesWithTrailers(trendingMovies)
                } catch (e: Exception) {
                    trendingMovies
                }
            },
            onSuccess = { movies ->
                updateState { state ->
                    state.copy(
                        movieTabUiState = state.movieTabUiState.copy(
                            trending = SectionUiState(
                                title = "trending",
                                media = movies.map { it.toMediaUiState() })
                        )
                    )
                }
                trendingSectionMoviesLoading(false)
            },
            onError = { e ->
                trendingSectionMoviesLoading(false)
                onError()
            }
        )
    }

    override fun onClickPlayButton(mediaIndex: Int, mediaType: MediaType) {
        val key =
            if (mediaType == MediaType.MOVIE) state.value.movieTabUiState.trending.media[mediaIndex].trailerKey
            else state.value.tvShowTabUiState.trending.media[mediaIndex].trailerKey
        emitNewEffect(HomeScreenEffect.GoToYoutube(key))

    }

    override fun onRefresh() {
        when(state.value.selectedTabIndex){
            0 -> loadMoviesLayoutData()
            1 -> loadSeriesLayoutData()
            2 -> loadGenres()
        }
    }

    private fun loadTopRatingMoviesSection() {
        tryToExecute(
            function = {
                topRatingSectionMoviesLoading(true)
                getTopRatedMoviesUseCase(1)
            },
            onSuccess = { movies ->
                updateState { state ->
                    state.copy(
                        movieTabUiState = state.movieTabUiState.copy(
                            topRated = SectionUiState(
                                title = "Top Rating",
                                media = movies.map { it.toMediaUiState() })
                        )
                    )
                }
                topRatingSectionMoviesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadNowPlayingMoviesSection() {
        tryToExecute(
            function = {
                nowPlayingSectionMoviesLoading(true)
                getNowPlayingMoviesUseCase(1)
            },
            onSuccess = { movies ->
                updateState { state ->
                    state.copy(
                        movieTabUiState = state.movieTabUiState.copy(
                            nowPlaying = SectionUiState(
                                title = "Now Playing",
                                media = movies.map { it.toMediaUiState() })
                        )
                    )
                }
                nowPlayingSectionMoviesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadUpComingMoviesSection() {
        tryToExecute(
            function = {
                upcomingSectionMoviesLoading(true)
                getUpcomingMoviesUseCase(1)
            },
            onSuccess = { movies ->
                updateState { state ->
                    state.copy(
                        movieTabUiState = state.movieTabUiState.copy(
                            upcoming = SectionUiState(
                                title = "Upcoming",
                                media = movies.map { it.toMediaUiState() })
                        )
                    )
                }
                upcomingSectionMoviesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadRecommendedMoviesSection() {
        tryToExecute(
            function = {
                recommendedSectionMoviesLoading(true)
                getRecommendedMoviesUseCase(1)
            },
            onSuccess = { movies ->
                updateState { state ->
                    state.copy(
                        movieTabUiState = state.movieTabUiState.copy(
                            moreRecommended = SectionUiState(
                                title = "More Recommended",
                                media = movies.map { it.toMediaUiState() })
                        )
                    )
                }
                recommendedSectionMoviesLoading(false)
            },
            onError = { onError() }
        )
    }


    /*************************************** Series Tab Section ***************************************/

    private fun trendingSectionSeriesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                tvShowTabUiState = state.tvShowTabUiState.copy(
                    trending = state.tvShowTabUiState.trending.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun topRatingSectionSeriesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                tvShowTabUiState = state.tvShowTabUiState.copy(
                    topRated = state.tvShowTabUiState.topRated.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun airingTodaySectionSeriesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                tvShowTabUiState = state.tvShowTabUiState.copy(
                    airingToday = state.tvShowTabUiState.airingToday.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun onAirSectionSeriesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                tvShowTabUiState = state.tvShowTabUiState.copy(
                    onTv = state.tvShowTabUiState.onTv.copy(
                        isLoading = isLoading
                    )
                )
            )
        }
    }

    private fun recommendedSectionSeriesLoading(isLoading: Boolean = true) {
        updateState { state ->
            state.copy(
                tvShowTabUiState = state.tvShowTabUiState.copy(
                    moreRecommended = state.tvShowTabUiState.moreRecommended.copy(isLoading = isLoading)
                )
            )
        }
    }

    override fun loadSeriesLayoutData() {
        loadSliderSeries()
        loadTopRatingSeriesSection()
        loadAiringTodaySeriesSection()
        loadOnAirSeriesSection()
        loadRecommendedSeriesSection()
    }

    private fun loadSliderSeries() {
        tryToExecute(
            function = {
                trendingSectionSeriesLoading(true)
                val series = getRecommendedSeriesUseCase(1)
                getSeriesWithTrailers(series)
            },
            onSuccess = { allSeries ->
                updateState { state ->
                    state.copy(
                        tvShowTabUiState = state.tvShowTabUiState.copy(
                            trending = SectionUiState(
                                title = "Trending",
                                media = allSeries.map { it.toMediaUiState() })
                        )
                    )
                }
                trendingSectionSeriesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadTopRatingSeriesSection() {
        tryToExecute(
            function = {
                topRatingSectionSeriesLoading(true)
                getTopRatedSeriesUseCase(1)
            },
            onSuccess = { allSeries ->
                updateState { state ->
                    state.copy(
                        tvShowTabUiState = state.tvShowTabUiState.copy(
                            topRated = SectionUiState(
                                title = "Top Rating",
                                media = allSeries.map { it.toMediaUiState() })
                        )
                    )
                }
                topRatingSectionSeriesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadAiringTodaySeriesSection() {
        tryToExecute(
            function = {
                airingTodaySectionSeriesLoading(true)
                getAiringTodaySeriesUseCase(1)
            },
            onSuccess = { allSeries ->
                updateState { state ->
                    state.copy(
                        tvShowTabUiState = state.tvShowTabUiState.copy(
                            airingToday = SectionUiState(
                                title = "Airing Today",
                                media = allSeries.map { it.toMediaUiState() })
                        )
                    )
                }
                airingTodaySectionSeriesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadOnAirSeriesSection() {
        tryToExecute(
            function = {
                onAirSectionSeriesLoading(true)
                getOnAirSeriesUseCase(1)
            },
            onSuccess = { allSeries ->
                updateState { state ->
                    state.copy(
                        tvShowTabUiState = state.tvShowTabUiState.copy(
                            onTv = SectionUiState(
                                title = "On Air",
                                media = allSeries.map { it.toMediaUiState() })
                        )
                    )
                }
                onAirSectionSeriesLoading(false)
            },
            onError = { onError() }
        )
    }

    private fun loadRecommendedSeriesSection() {
        tryToExecute(
            function = {
                recommendedSectionSeriesLoading(true)
                getRecommendedSeriesUseCase(1)
            },
            onSuccess = { allSeries ->
                updateState { state ->
                    state.copy(
                        tvShowTabUiState = state.tvShowTabUiState.copy(
                            moreRecommended = SectionUiState(
                                title = "More Recommended",
                                media = allSeries.map { it.toMediaUiState() })
                        )
                    )
                }
                recommendedSectionSeriesLoading(false)
            },
            onError = { onError() }
        )
    }
}