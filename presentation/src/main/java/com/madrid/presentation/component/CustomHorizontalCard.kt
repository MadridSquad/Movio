package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

@Composable
fun CustomHorizontalCard(
    primaryTextForCustomTextTitel: String,
    listOfMedia: List<MediaUiState>,
    modifier: Modifier = Modifier,
    headerModifier: Modifier  = Modifier,
    secondaryTextForCustomTextTitel: String? = null,
    endIconForCustomTextTitel: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    onMediaClick: (MediaUiState) -> Unit = {},
) {
    Column(modifier = modifier) {
        CustomTextTitel(
            primaryText = primaryTextForCustomTextTitel,
            secondaryText = secondaryTextForCustomTextTitel,
            endIcon = endIconForCustomTextTitel,
            onSeeAllClick = onSeeAllClick,
            modifier = headerModifier
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(200.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOfMedia) { media ->
                MovioVerticalCard(
                    description = media.title,
                    movieImage = media.imageUrl,
                    rate = media.rating,
                    width = 124.dp,
                    height = 160.dp,
                    paddingValue = 8.dp,
                    onClick = { onMediaClick(media) }
                )
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
            category = "Sci-Fi"
        ),
        MediaUiState(
            id = "2",
            mediaType = MediaType.TV_SERIES,
            title = "Breaking Bad",
            imageUrl = "https://via.placeholder.com/150",
            rating = "9.5",
            category = "Drama"
        ),
        MediaUiState(
            id = "3",
            mediaType = MediaType.MOVIE,
            title = "The Dark Knight",
            imageUrl = "https://via.placeholder.com/150",
            rating = "9.0",
            category = "Action"
        )
    )

    MovioTheme {
        CustomHorizontalCard(
            primaryTextForCustomTextTitel = "Trending Now",
            secondaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.see_all),
            endIconForCustomTextTitel = painterResource(R.drawable.outline_alt_arrow_left),
            listOfMedia = fakeMediaList,
            onSeeAllClick = {},
            onMediaClick = {}
        )
    }
}