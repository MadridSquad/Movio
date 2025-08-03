package com.madrid.presentation.viewModel.homeViewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.madrid.domain.entity.User
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.domain.usecase.movie.GetMoviesByGenreIdUseCase
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
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase
) : BaseViewModel<HomeScreenState, HomeScreenEffect>(
    HomeScreenState()
), HomeInteractionListener {
    init {
        loadGenres()
        loadFileImage()
    }

    private fun loadFileImage() {
        tryToExecute(
            function ={
                getCurrentUserDetailsUseCase()
            },
            onSuccess = {user->
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
                            selectedCategory = genres.first()
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
        val allCategory = listOf(
            CategoryUiState(id = -1, name = "All")
        )
        return allCategory + genres.map { it.toCategoryUiState() }
    }

    private fun fetchMediaByCategory(genreId: Int?, sortingType: SortingType) {
        startLoading()
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
                    media = result
                )
            )
        }
    }

    override fun onSelectTab() {
        TODO("Not yet implemented")
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
        val selectedCategoryId = state.value.categoryTabUiState.selectedCategory.id
        val genreId = if (selectedCategoryId == -1) null else selectedCategoryId
        fetchMediaByCategory(genreId, sortType)
    }

    override fun onMediaSelected(mediaId: Int, mediaType: MediaType) {
        emitNewEffect(HomeScreenEffect.NavigateToMediaDetails(mediaId, mediaType))
    }

    private fun startLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onError(errorMessage: String = "") {
        updateState { it.copy(isLoading = false, errorMessage = errorMessage) }
    }

    /*************************************** Movies Section ***************************************/


    override fun loadMoviesLayoutData() {
        loadSliderMovies()
        loadTopRatingMoviesSection()
        loadNowPlayingMoviesSection()
        loadUpComingMoviesSection()
        loadRecommendedMoviesSection()
    }

    private fun loadSliderMovies() {
        tryToExecute(
            function = { getTrendingMoviesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadTopRatingMoviesSection() {
        tryToExecute(
            function = { getTopRatedMoviesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadNowPlayingMoviesSection() {
        tryToExecute(
            function = { getNowPlayingMoviesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadUpComingMoviesSection() {
        tryToExecute(
            function = { getUpcomingMoviesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadRecommendedMoviesSection() {
        tryToExecute(
            function = { getRecommendedMoviesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }


    /*************************************** Series Tab Section ***************************************/


    override fun loadSeriesLayoutData() {
        loadSliderSeries()
        loadTopRatingSeriesSection()
        loadAiringTodaySeriesSection()
        loadOnAirSeriesSection()
        loadRecommendedSeriesSection()
    }

    private fun loadSliderSeries() {
        tryToExecute(
            function = { getRecommendedSeriesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadTopRatingSeriesSection() {
        tryToExecute(
            function = { getTopRatedSeriesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadAiringTodaySeriesSection() {
        tryToExecute(
            function = { getAiringTodaySeriesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadOnAirSeriesSection() {
        tryToExecute(
            function = { getOnAirSeriesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

    private fun loadRecommendedSeriesSection() {
        tryToExecute(
            function = { getRecommendedSeriesUseCase(1) },
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
            },
            onError = { onError() }
        )
    }

}