package com.madrid.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.homeViewModel.CategoryUiState
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

@Composable
fun CustomHorizontalCard(
    primaryTextForCustomTextTitle: String,
    listOfMedia: List<MediaUiState>,
    modifier: Modifier = Modifier,
    headerModifier: Modifier = Modifier,
    startIconForPrimaryTextTitle: Painter? = null,
    secondaryTextForCustomTextTitle: String? = null,
    endIconForCustomTextTitle: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    onMediaClick: (MediaUiState) -> Unit = {},
) {
    Column(modifier = modifier) {
        CustomTextTitle(
            modifier = headerModifier.padding(bottom = 12.dp),
            primaryText = primaryTextForCustomTextTitle,
            startIcon = startIconForPrimaryTextTitle,
            secondaryText = secondaryTextForCustomTextTitle,
            endIcon = endIconForCustomTextTitle,
            onSeeAllClick = onSeeAllClick,
            isListEmpty = listOfMedia.isEmpty()
        )
        AnimatedVisibility(listOfMedia.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(200.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(listOfMedia) { media ->
                    MovioVerticalCard(
                        description = media.title,
                        movieImage = media.imageUrl,
                        rate = media.rating.take(3),
                        width = 124.dp,
                        height = 160.dp,
                        onClick = { onMediaClick(media) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomHorizontalCardPreview() {
    val fakeMediaList = listOf(
        MediaUiState(
            id = "1",
            mediaType = MediaType.MOVIE,
            title = "Inception",
            imageUrl = "https://via.placeholder.com/150",
            rating = "8.8",
            category = listOf(
                CategoryUiState(name = "Sci-Fi")
            ),
        ),
        MediaUiState(
            id = "2",
            mediaType = MediaType.TV_SHOW,
            title = "Breaking Bad",
            imageUrl = "https://via.placeholder.com/150",
            rating = "9.5",
            category = listOf(
                CategoryUiState(name = "Drama")
            ),
        ),
        MediaUiState(
            id = "3",
            mediaType = MediaType.MOVIE,
            title = "The Dark Knight",
            imageUrl = "https://via.placeholder.com/150",
            rating = "9.0",
            category = listOf(
                CategoryUiState(name = "Action")
            ),
        )
    )

    MovioTheme {
        CustomHorizontalCard(
            primaryTextForCustomTextTitle = "Trending Now",
            secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
            endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
            listOfMedia = fakeMediaList,
            onSeeAllClick = {},
            onMediaClick = {}
        )
    }
}