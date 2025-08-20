package com.madrid.presentation.viewModel.searchViewModel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.madrid.domain.usecase.search.AddRecentSearchUseCase
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetArtistsByQueryUseCase
import com.madrid.domain.usecase.search.GetExploreMoreMovieUseCase
import com.madrid.domain.usecase.search.GetMoviesByQueryUseCase
import com.madrid.domain.usecase.search.GetRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.search.GetSeriesByQueryUseCase
import com.madrid.domain.usecase.search.RemoveRecentSearchUseCase
import com.madrid.presentation.pagination.ExplorePagingSource
import com.madrid.presentation.pagination.SearchArtistPagingSource
import com.madrid.presentation.pagination.SearchMoviePagingSource
import com.madrid.presentation.pagination.SearchSeriesPagingSource
import com.madrid.presentation.screens.searchScreen.utils.FilterPagesItem
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.uiStateMapper.toArtistUiState
import com.madrid.presentation.viewModel.uiStateMapper.toMovieUiState
import com.madrid.presentation.viewModel.uiStateMapper.toSeriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.text.contains

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getArtistsByQueryUseCase: GetArtistsByQueryUseCase,
    private val getMoviesByQueryUseCase: GetMoviesByQueryUseCase,
    private val getSeriesByQueryUseCase: GetSeriesByQueryUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getExploreMoreMovieUseCase: GetExploreMoreMovieUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val removeRecentSearchUseCase: RemoveRecentSearchUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase
) : BaseViewModel<SearchScreenState, Nothing>(
    SearchScreenState()
) {

    init {
        loadRecentSearches()
        loadInitialData()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            function = { getRecentSearchesUseCase() },
            onSuccess = ::onUpdateRecentSuccess,
            onError = { },
        )
    }

    fun updateSearchQuery(
        newSearchQuery: String,
    ) {
        updateState { searchScreenState ->
            searchScreenState.copy(
                isDoAction = !searchScreenState.isDoAction,
                searchUiState = searchScreenState.searchUiState.copy(
                    searchQuery = newSearchQuery,
                    isSearchQueryChange = true
                ),

            )
        }
        onChangeRecentSearchUiState()

    }
    fun addRecentSearch(recentSearch: String) {
        tryToExecute(
            function = {
                addRecentSearchUseCase(item = recentSearch)
                getRecentSearchesUseCase()
            },
            onSuccess = ::onUpdateRecentSuccess,
            onError = {}
        )
    }

    fun clearAll() {
        updateState {searchScreenStat->
            searchScreenStat.copy(
                isDoAction = !searchScreenStat.isDoAction
            )
        }

        tryToExecute(
            function = {
                clearAllRecentSearchesUseCase()
                getRecentSearchesUseCase()
            },
            onSuccess = ::onUpdateRecentSuccess,
            onError = {}
        )
    }

    fun removeRecentSearch(searchItem: String) {
        updateState {searchScreenStat->
            searchScreenStat.copy(
                isDoAction = !searchScreenStat.isDoAction
            )
        }
        tryToExecute(
            function = {
                removeRecentSearchUseCase(searchItem)
                getRecentSearchesUseCase()
            },
            onSuccess = {
                onUpdateRecentSuccess(it)

            },
            onError = {},
        )
    }
    fun changeValueOfIsChangeSearchQuery(){
        updateState {searchScreenState->
           searchScreenState.copy(
               searchScreenState.searchUiState.copy(
                   isSearchQueryChange = false
               )
           )
        }
    }

    private fun loadInitialData() {
        tryToExecute(
            function = {
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
                SearchMoviePagingSource(query, getMoviesByQueryUseCase)
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
                SearchSeriesPagingSource(query, getSeriesByQueryUseCase)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toSeriesUiState() }
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
                    getMoviesByQueryUseCase = getMoviesByQueryUseCase
                )
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toMovieUiState() }
                }

                Log.d("SearchViewModel", "topResult OO: $result")

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
                SearchArtistPagingSource(query, getArtistsByQueryUseCase)
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toArtistUiState() }
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
        updateState {searchScreenState->
            searchScreenState.copy(
                allRecentSearchTexts = result,
            )
        }
        onChangeRecentSearchUiState()
    }

    private fun onChangeRecentSearchUiState() {
        updateState {searchScreenState->
            searchScreenState.copy(
                recentSearchUiState = if (searchScreenState.searchUiState.searchQuery.isEmpty() ||
                    searchScreenState.searchUiState.searchQuery.isBlank()
                ) {
                    searchScreenState.allRecentSearchTexts
                } else {
                    val queryWords = searchScreenState
                        .searchUiState
                        .searchQuery
                        .trim()
                        .lowercase()
                        .split(" ")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }

                   searchScreenState
                        .allRecentSearchTexts
                        .filter { recentText ->
                            queryWords.all { recentText.contains(it) }
                        }
                }
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
        if(state.value.searchUiState.searchQuery.isEmpty() ){
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
                },
                onStartLoading = {updateState {
                    it.copy(
                        searchUiState = it.searchUiState.copy(
                            isLoading = true,
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
                }
            )
        }
        else{
            when(typeOfFilterSearch){
                FilterPagesItem.TOP_RATED -> topResult(state.value.searchUiState.searchQuery)
                FilterPagesItem.MOVIES -> searchFilteredMovies(state.value.searchUiState.searchQuery)
                FilterPagesItem.SERIES -> searchSeries(state.value.searchUiState.searchQuery)
                FilterPagesItem.ARTISTS -> artists(state.value.searchUiState.searchQuery)
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
        }else {
            val numberSet = mutableSetOf<Int>()
            query.trim()
                .lowercase()
                .split(" ")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .forEach { pureQuery ->
                    var searchIndex = 0

                    while (true) {
                        val foundIndex = fullText.lowercase().indexOf(pureQuery, searchIndex)
                        if (foundIndex == -1) break

                        for (i in foundIndex until foundIndex + pureQuery.length) {
                            numberSet.add(i)
                        }

                        searchIndex = foundIndex + pureQuery.length
                    }
                }

            builder.pushStyle(textStyle.copy(color = normalColor).toSpanStyle())
            builder.append(fullText)

            numberSet.toRanges().forEach { range ->
                builder.addStyle(
                    textStyle.copy(color = matchColor).toSpanStyle(),
                    start = range.first,
                    end = range.last + 1
                )
            }
            return builder.toAnnotatedString()

        }
    }

    private fun Set<Int>.toRanges(): List<IntRange> {
        if (isEmpty()) return emptyList()

        val sorted = this.sorted()
        val ranges = mutableListOf<IntRange>()

        var rangeStart = sorted.first()
        var prev = sorted.first()

        for (i in 1 until sorted.size) {
            val current = sorted[i]
            if (current != prev + 1) {
                ranges.add(rangeStart..prev)
                rangeStart = current
            }
            prev = current
        }
        ranges.add(rangeStart..prev)

        return ranges
    }

    fun doAction(){
        updateState {searchScreenStat->
            searchScreenStat.copy(
                isDoAction = !searchScreenStat.isDoAction
            )
        }
    }

}