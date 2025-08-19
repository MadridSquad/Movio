package com.madrid.presentation.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.emptyRecentSearch
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.refreshScreenHolder.RefreshScreenHolder
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.FilterSearchScreen
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.ForYouAndExploreScreen
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.recentSearchScreen
import com.madrid.presentation.screens.searchScreen.utils.FilterPagesItem
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState
import com.madrid.presentation.viewModel.searchViewModel.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    var typeOfFilterSearch by remember { mutableStateOf(FilterPagesItem.TOP_RATED) }
    val navController = LocalNavController.current

    RefreshScreenHolder(
        refreshState = uiState.searchUiState.refreshState,
        onRefresh = { viewModel.onRefresh(typeOfFilterSearch) }
    ) {
        ContentSearchScreen(
            isError = uiState.searchUiState.isError,
            typeOfFilterSearch = typeOfFilterSearch,
            onSeriesClick = { seriesId ->
                navController.navigate(
                    Destinations.SeriesDetailsScreen(
                        seriesId = seriesId,
                        seasonNumber = 1
                    )
                )
            },
            addRecentSearch = {
                viewModel.addRecentSearch(it)
            },
            modifier = modifier,
            topRated = uiState.filteredScreenUiState.topResult.collectAsLazyPagingItems(),
            movies = uiState.filteredScreenUiState.movie.collectAsLazyPagingItems(),
            series = uiState.filteredScreenUiState.series.collectAsLazyPagingItems(),
            artist = uiState.filteredScreenUiState.artist.collectAsLazyPagingItems(),
            onClickTopRated = {
                typeOfFilterSearch = FilterPagesItem.TOP_RATED
                viewModel.topResult(uiState.searchUiState.searchQuery)
            },
            onClickMovies = {
                typeOfFilterSearch = FilterPagesItem.MOVIES
                viewModel.searchFilteredMovies(uiState.searchUiState.searchQuery)
            },
            onClickSeries = {
                typeOfFilterSearch = FilterPagesItem.SERIES
                viewModel.searchSeries(uiState.searchUiState.searchQuery)
            },
            onClickArtist = {
                typeOfFilterSearch = FilterPagesItem.ARTISTS
                viewModel.artists(uiState.searchUiState.searchQuery)
            },

            forYouMovies = uiState.searchUiState.forYouMovies,
            exploreMoreMovies = uiState.searchUiState.exploreMoreMovies.collectAsLazyPagingItems(),
            searchQuery = uiState.searchUiState.searchQuery,
            onSearchQueryChange = { query ->
                viewModel.updateSearchQuery(query)

            },
            onMovieClick = { movie ->
                navController.navigate(Destinations.MovieDetailsScreen(movie.id.toInt()))
            },
            isLoading = uiState.searchUiState.isLoading,
            searchHistory = uiState.recentSearchUiState,
            onSearchItemClick = { viewModel.updateSearchQuery(it) },
            onRemoveItem = { viewModel.removeRecentSearch(it) },
            onClearAll = { viewModel.clearAll() },
            onClickSeeAll = {
                navController.navigate(Destinations.SeeAllForYouScreen)
            },
            highLightRecentSearch = viewModel::highlightCharactersInText,
            onTopResultClick = { movieId ->
                navController.navigate(Destinations.MovieDetailsScreen(movieId))
            },
            onSearchedClick = { movieId ->
                navController.navigate(Destinations.MovieDetailsScreen(movieId))
            },
            onArtistClick = { actorId ->
                navController.navigate(Destinations.ActorDetails(actorId))
            },
            isDoAction = uiState.isDoAction,
            isChangeSearchQuery = uiState.searchUiState.isSearchQueryChange,
            changeValueOfIsChangeSearchQuery = viewModel::changeValueOfIsChangeSearchQuery,
            doAction = viewModel::doAction
        )
        uiState.searchUiState.errorMessage?.let { errorMsg ->
            LaunchedEffect(errorMsg) {

            }
        }
        if (uiState.searchUiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MovioIcon(
                    painter = painterResource(R.drawable.loading),
                    contentDescription = "Loading",
                    tint = Theme.color.brand.primary
                )
            }
        }
    }
}


@OptIn(FlowPreview::class)
@Composable
fun ContentSearchScreen(
    isError: Boolean,
    typeOfFilterSearch: FilterPagesItem,
    addRecentSearch: (String) -> Unit,
    topRated: LazyPagingItems<SearchScreenState.MovieUiState>,
    movies: LazyPagingItems<SearchScreenState.MovieUiState>,
    series: LazyPagingItems<SearchScreenState.SeriesUiState>,
    artist: LazyPagingItems<SearchScreenState.ArtistUiState>,
    onClickTopRated: () -> Unit,
    onClickMovies: () -> Unit,
    onClickSeries: () -> Unit,
    onClickArtist: () -> Unit,
    modifier: Modifier = Modifier,
    forYouMovies: List<SearchScreenState.MovieUiState> = emptyList(),
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit,
    onSearchBarClick: () -> Unit = {},
    onMovieClick: (SearchScreenState.MovieUiState) -> Unit = {},
    searchHistory: List<String>,
    onSearchItemClick: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
    onClearAll: () -> Unit,
    isLoading: Boolean = false,
    onClickSeeAll: () -> Unit,
    onSeriesClick: (Int) -> Unit,
    onTopResultClick: (Int) -> Unit,
    onSearchedClick: (Int) -> Unit,
    onArtistClick: (Int) -> Unit,
    isChangeSearchQuery : Boolean,
    isDoAction : Boolean,
    doAction : () -> Unit ,
    changeValueOfIsChangeSearchQuery : () -> Unit,
    highLightRecentSearch: (String, String, Color, Color, TextStyle) -> AnnotatedString,
) {
    val showSearchResults = searchQuery.isNotBlank()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showRecentSearch by remember { mutableIntStateOf(0) }
    var isPressSearchIconInKeyboard by remember { mutableIntStateOf(0) }
    var previousSelectedTabIndex by remember { mutableIntStateOf(-1) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isDoAction) {
        if(searchQuery.isEmpty() || searchQuery.isBlank() || isPressSearchIconInKeyboard == 1){
            showRecentSearch = 0
            isPressSearchIconInKeyboard = 0
        }
        else{
            showRecentSearch = 1
            snapshotFlow { searchQuery }
                .debounce(1500)
                .collect { query ->
                    showRecentSearch = 0

                    if (query.isNotBlank() && isChangeSearchQuery ) {
                        onSearchBarClick()
                        changeValueOfIsChangeSearchQuery()
                        addRecentSearch(query)
                        when (typeOfFilterSearch) {
                            FilterPagesItem.TOP_RATED -> onClickTopRated()
                            FilterPagesItem.MOVIES -> onClickMovies()
                            FilterPagesItem.SERIES -> onClickSeries()
                            FilterPagesItem.ARTISTS -> onClickArtist()
                        }
                    }
                }
        }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 158.dp),
            modifier = modifier
                .background(Theme.color.surfaces.surface),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                BasicTextInputField(
                    value = searchQuery,
                    onValueChange = { newQuery ->
                        onSearchQueryChange(newQuery)
                        showRecentSearch = 1
                    },
                    borderBrushColors = null,
                    hintText = stringResource(com.madrid.presentation.R.string.searchdot),
                    startIconPainter = painterResource(R.drawable.search_normal),
                    endIconPainter = painterResource(R.drawable.outline_add),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clickable { onSearchBarClick() },
                    onClickEndIcon = { onSearchQueryChange("") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        isPressSearchIconInKeyboard = 1
                        doAction()
                        if (searchQuery.isNotBlank() && isChangeSearchQuery ) {
                            onSearchBarClick()
                            changeValueOfIsChangeSearchQuery()
                            addRecentSearch(searchQuery)
                            when (typeOfFilterSearch) {
                                FilterPagesItem.TOP_RATED -> onClickTopRated()
                                FilterPagesItem.MOVIES -> onClickMovies()
                                FilterPagesItem.SERIES -> onClickSeries()
                                FilterPagesItem.ARTISTS -> onClickArtist()
                            }
                        }
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )
        }

            if (searchQuery.isEmpty() && showRecentSearch != 1) {
                ForYouAndExploreScreen(
                    showSearchResults = showSearchResults,
                    isLoading = isLoading,
                    isError = isError,
                    forYouMovies = forYouMovies,
                    onMovieClick = onMovieClick,
                    exploreMoreMovies = exploreMoreMovies,
                    onClickSeeAll = { onClickSeeAll() },
                    parentPadding = 16.dp
                )
            }

            if (showRecentSearch == 1 && searchHistory.isNotEmpty()) {
                recentSearchScreen(
                    searchHistory = searchHistory,
                    searchQuery = searchQuery,
                    onSearchItemClick = { onSearchItemClick(it) },
                    onRemoveItem = { onRemoveItem(it) },
                    onClearAll = { onClearAll() },
                    highlightCharactersInText = highLightRecentSearch,
                )
            }
            if (showRecentSearch == 1 && searchHistory.isEmpty()) {
                emptyRecentSearch()
            }
        }

        if (searchQuery.isNotEmpty() && showRecentSearch != 1) {

            FilterSearchScreen(
                typeOfFilterSearch = typeOfFilterSearch,
                topRated = topRated,
                movies = movies,
                series = series,
                artist = artist,
                selectedTabIndex = selectedTabIndex,

                onChangeSelectedTabIndex = { newIndex ->
                    if (newIndex != selectedTabIndex) {
                        previousSelectedTabIndex = selectedTabIndex
                        selectedTabIndex = newIndex

                        when (newIndex) {
                            0 -> onClickTopRated()
                            1 -> onClickMovies()
                            2 -> onClickSeries()
                            3 -> onClickArtist()
                        }
                    }
                },
                onChangeTypeFilterSearch = {},
                onSeriesClick = { seriesId ->
                    onSeriesClick(seriesId)
                },
                onMovieClick = { moviesId ->
                    onSearchedClick(moviesId)
                },
                onActorClick = { actorId ->
                    onArtistClick(actorId)
                },
                onTopResultClick = { movieId ->
                    onTopResultClick(movieId)
                }
            )
        }
    }

}