package com.madrid.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.component.videoLibrary.VideoLibrary
import com.madrid.presentation.screens.libraryScreen.component.AddWatchListItem
import com.madrid.presentation.viewModel.shared.WatchListUiState

@Composable
fun CustomHorizontalCardForWatchList(
    primaryTextForCustomTextTitle: String,
    listOfWatch: List<WatchListUiState>,
    modifier: Modifier = Modifier,
    headerModifier: Modifier = Modifier,
    startIconForPrimaryTextTitle: Painter? = null,
    secondaryTextForCustomTextTitle: String? = null,
    endIconForCustomTextTitle: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    onWatchListClick: (WatchListUiState) -> Unit = {},
    onEmptyWatchListClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        CustomTextTitle(
            modifier = headerModifier.padding(bottom = 12.dp),
            primaryText = primaryTextForCustomTextTitle,
            startIcon = startIconForPrimaryTextTitle,
            secondaryText = secondaryTextForCustomTextTitle,
            endIcon = endIconForCustomTextTitle,
            onSeeAllClick = onSeeAllClick,
        )

        AnimatedVisibility(listOfWatch.isEmpty()) {
            AddWatchListItem(modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable(onClick = onEmptyWatchListClick))
        }
        AnimatedVisibility(listOfWatch.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier,
            ) {
                items(listOfWatch, key = { it.id }) { watchList ->
                    VideoLibrary(
                        onClick = { onWatchListClick(watchList) },
                        videosNumber = watchList.numberOfVideos,
                        title = watchList.watchListTitle,
                        posterUrl = watchList.posterUrl,
                        modifier = Modifier
                            .width(158.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomHorizontalCardForWatchListPreview() {
    MovioTheme {
        CustomHorizontalCardForWatchList(
            primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.watchlist),
            listOfWatch = listOf(),
            modifier = Modifier,
        )
    }
}