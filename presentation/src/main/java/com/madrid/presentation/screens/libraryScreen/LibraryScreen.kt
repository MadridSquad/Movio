package com.madrid.presentation.screens.libraryScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.CustomHorizontalCardForWatchList
import com.madrid.presentation.screens.libraryScreen.component.LibraryScreenHeader
import com.madrid.presentation.viewModel.libraryViewModel.WatchListItemUiState
import com.madrid.presentation.viewModel.shared.MediaUiState

@Composable
fun LibraryScreen() {
    LibraryScreenContent()
}

@Composable
private fun LibraryScreenContent(
    watchList: List<WatchListItemUiState> = listOf(),
    onClickWatchListViewAll: () -> Unit = {},
    onClickWatchListItem: (WatchListItemUiState) -> Unit = {},
    favoriteList: List<MediaUiState> = listOf(),
    onClickFavoriteListViewAll: () -> Unit = {},
    onFavoriteItemClick: (MediaUiState) -> Unit = {},
    historyList: List<MediaUiState> = listOf(),
    onClickHistoryListViewAll: () -> Unit = {},
    onHistoryItemClick: (MediaUiState) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
    ) {
        item() {
            LibraryScreenHeader(stringResource(com.madrid.presentation.R.string.Library))
        }
        item {
            CustomHorizontalCardForWatchList(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.watchlist),
                listOfWatch = watchList,
                modifier = Modifier,
                headerModifier = Modifier.padding(horizontal = 16.dp),
                startIconForPrimaryTextTitle = painterResource(R.drawable.outline_minimalistic),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.view_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = onClickWatchListViewAll,
                onWatchListClick = onClickWatchListItem
            )
        }
        item {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.favorites),
                listOfMedia = favoriteList,
                modifier = Modifier,
                headerModifier = Modifier.padding(horizontal = 16.dp),
                startIconForPrimaryTextTitle = painterResource(R.drawable.outline_heart),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.view_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = onClickFavoriteListViewAll,
                onMediaClick = onFavoriteItemClick
            )
        }
        item {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.history),
                listOfMedia = historyList,
                modifier = Modifier,
                headerModifier = Modifier.padding(horizontal = 16.dp),
                startIconForPrimaryTextTitle = painterResource(R.drawable.outline_history),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.view_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = onClickHistoryListViewAll,
                onMediaClick = onHistoryItemClick
            )
        }
    }
}