package com.madrid.presentation.viewModel.searchViewModel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.madrid.domain.usecase.GetExploreMoreMovieUseCase
import com.madrid.domain.usecase.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.searchUseCase.ArtistUseCase
import com.madrid.domain.usecase.searchUseCase.MovieUseCase
import com.madrid.domain.usecase.searchUseCase.RecentSearchUseCase
import com.madrid.domain.usecase.searchUseCase.SeriesUseCase
import com.madrid.presentation.screens.searchScreen.paging.ExplorePagingSource
import com.madrid.presentation.screens.searchScreen.paging.SearchArtistPagingSource
import com.madrid.presentation.screens.searchScreen.paging.SearchMoviePagingSource
import com.madrid.presentation.screens.searchScreen.paging.SearchSeriesPagingSource
import com.madrid.presentation.screens.searchScreen.utils.FilterPagesItem
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.uiStateMapper.toArtistUiState
import com.madrid.presentation.viewModel.uiStateMapper.toMovieUiState
import com.madrid.presentation.viewModel.uiStateMapper.toSeriesUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SearchViewModel(
    private val artistUseCase: ArtistUseCase,
    private val movieUseCase: MovieUseCase,
    private val seriesUseCase: SeriesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getExploreMoreMovieUseCase: GetExploreMoreMovieUseCase,
    private val recentSearchUseCase: RecentSearchUseCase,
) : BaseViewModel<SearchScreenState, Nothing>(
    SearchScreenState()
) {

    init {
        loadRecentSearches()
        loadInitialData()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            function = { recentSearchUseCase.getRecentSearches() },
            onSuccess = ::onUpdateRecentSuccess,
            onError = { },
        )
    }

    fun addRecentSearch(recentSearch: String) {
        tryToExecute(
            function = {
                recentSearchUseCase.addRecentSearch(item = recentSearch)
                recentSearchUseCase.getRecentSearches()
            },
            onSuccess = { result -> updateState { it.copy(recentSearchUiState = result) } },
            onError = {}
        )
    }

    fun clearAll() {
        tryToExecute(
            function = {
                recentSearchUseCase.clearAllRecentSearches()
                recentSearchUseCase.getRecentSearches()
            },
            onSuccess = { result -> updateState { it.copy(recentSearchUiState = result) } },
            onError = {}
        )
    }

    fun removeRecentSearch(searchItem: String) {
        tryToExecute(
            function = {
                recentSearchUseCase.removeRecentSearch(searchItem)
                recentSearchUseCase.getRecentSearches()
            },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        recentSearchUiState = result
                    )
                }
            },
            onError = {},
        )
    }

    private fun loadInitialData() {
        tryToExecute(
            function = {
                ::onLoadInitialData
                getRecommendedMovieUseCase(page = 1)
            },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        searchUiState = it.searchUiState.copy(
                            forYouMovies = result.map { movie -> movie.toMovieUiState() },
                            isLoading = false
                        )
                    )
                }
            },
            onError = { throwValue ->
                updateState {
                    it.copy(
                        searchUiState = it.searchUiState.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = throwValue.message
                        )
                    )
                }
            },
        )

        launchPagingRequest(
            pagingSourceFactory = {
                ExplorePagingSource(getExploreMoreMovieUseCase)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toMovieUiState() }
                }

                updateState {
                    it.copy(
                        searchUiState = it.searchUiState.copy(
                            exploreMoreMovies = result,
                            isLoading = false
                        )
                    )
                }
            }
        )
    }

    fun searchFilteredMovies(query: String) {
        launchPagingRequest(
            pagingSourceFactory = {
                SearchMoviePagingSource(query, movieUseCase)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toMovieUiState() }
                }

                updateState { current ->
                    current.copy(
                        filteredScreenUiState = current.filteredScreenUiState.copy(
                            movie = result,
                            isLoading = false
                        ),
                        searchUiState = current.searchUiState.copy(isLoading = false)
                    )
                }
            }
        )
    }

    fun searchSeries(query: String) {
        launchPagingRequest(
            pagingSourceFactory = {
                SearchSeriesPagingSource(query, seriesUseCase)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { series -> series.toSeriesUiState() }
                }
                (::onUpdateSeriesSearch)(result)
            }
        )
    }

    fun topResult(query: String) {
        launchPagingRequest(
            pagingSourceFactory = {
                SearchMoviePagingSource(
                    query = query,
                    movieUseCase = movieUseCase
                )
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toMovieUiState() }
                }
                Log.d("SearchViewModel", "topResult OO: ${result.toString()}")

                updateState { current ->
                    current.copy(
                        filteredScreenUiState = current.filteredScreenUiState.copy(
                            topResult = result,
                            isLoading = false
                        ),
                        searchUiState = current.searchUiState.copy(isLoading = false)

                    )
                }
            }
        )
    }

    fun artists(query: String) {
        launchPagingRequest(
            pagingSourceFactory = {
                SearchArtistPagingSource(query, artistUseCase)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { artist ->
                        artist.toArtistUiState()
                    }
                }

                updateState { current ->
                    current.copy(
                        filteredScreenUiState = current.filteredScreenUiState.copy(
                            artist = result,
                            isLoading = false
                        ),
                        searchUiState = current.searchUiState.copy(isLoading = false)

                    )
                }
            }
        )
    }


    private fun onUpdateRecentSuccess(result: List<String>) {
        updateState {
            it.copy(
                recentSearchUiState = result
            )
        }
    }

    private fun onUpdateSearchMovie(result: Flow<PagingData<SearchScreenState.MovieUiState>>) {
        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    topResult = result,
                    isLoading = false
                )
            )
        }
    }

    private fun onUpdateSeriesSearch(result: Flow<PagingData<SearchScreenState.SeriesUiState>>) {
        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    series = result,
                    isLoading = false
                ),
                searchUiState = current.searchUiState.copy(isLoading = false)
            )
        }
    }

    private fun onLoadInitialData() {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(
                    isLoading = true,
                    isError = false,
                    errorMessage = null
                )
            )
        }
    }

    private fun onUpdateArtistSearch(result: Flow<PagingData<SearchScreenState.ArtistUiState>>) {
        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    artist = result,
                    isLoading = false
                )
            )
        }
    }

    private companion object {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 5
        )
    }

    fun onRefresh(
        searchQuery : String,
        typeOfFilterSearch : FilterPagesItem
    ) {
        updateState { current ->
            current.copy(
                searchUiState = current.searchUiState.copy(
                    refreshState = true,
                    errorMessage = null,
                    isError = false
                )
            )
        }
        if(searchQuery.isEmpty() ){
            tryToExecute(
                function = {
                    getRecommendedMovieUseCase(page = 1)
                },
                onSuccess = { result ->
                    updateState {
                        it.copy(
                            searchUiState = it.searchUiState.copy(
                                forYouMovies = result.map { movie -> movie.toMovieUiState() },
                                isLoading = false,
                                refreshState = false,
                            )
                        )
                    }
                },
                onError = { throwValue ->
                    updateState {
                        it.copy(
                            searchUiState = it.searchUiState.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = throwValue.message
                            )
                        )
                    }
                },
            )

            launchPagingRequest(
                pagingSourceFactory = {
                    ExplorePagingSource(getExploreMoreMovieUseCase)
                },
                onSuccess = { pagingFlow ->
                    val result = pagingFlow.map { pagingData ->
                        pagingData.map { it.toMovieUiState() }
                    }

                    updateState {
                        it.copy(
                            searchUiState = it.searchUiState.copy(
                                exploreMoreMovies = result,
                                isLoading = false,
                                refreshState = false,
                            )
                        )
                    }
                }
            )
        }
        else{
            when(typeOfFilterSearch){
                FilterPagesItem.TOP_RATED -> topResult(searchQuery)
                FilterPagesItem.MOVIES -> searchFilteredMovies(searchQuery)
                FilterPagesItem.SERIES -> searchSeries(searchQuery)
                FilterPagesItem.ARTISTS -> artists(searchQuery)
            }
            updateState { current ->
                current.copy(
                    searchUiState = current.searchUiState.copy(
                        refreshState = false,
                    )
                )
            }
        }
    }

    fun <T : Any> launchPagingRequest(
        pagingSourceFactory: () -> PagingSource<Int, T>,
        onSuccess: (Flow<PagingData<T>>) -> Unit,
        config: PagingConfig = PagingConfig(pageSize = 20),
    ) {
        try {
            updateState {
                it.copy(
                    searchUiState = it.searchUiState.copy(
                        isLoading = true,
                    )
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
                    searchUiState = it.searchUiState.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = e.message
                    )
                )
            }
        }
    }

    fun highlightCharactersInText(
        fullText: String,
        query: String,
        matchColor: Color,
        normalColor: Color,
        textStyle: TextStyle
    ): AnnotatedString {
        val builder = AnnotatedString.Builder()

        if (query.isBlank()) {
            builder.pushStyle(textStyle.copy(color = normalColor).toSpanStyle())
            builder.append(fullText)
            return builder.toAnnotatedString()
        }

        fullText.forEach { char ->
            val isMatch = query.contains(char, ignoreCase = true)
            val color = if (isMatch) matchColor else normalColor

            builder.pushStyle(textStyle.copy(color = color).toSpanStyle())
            builder.append(char.toString())
            builder.pop()
        }

        return builder.toAnnotatedString()
    }

}