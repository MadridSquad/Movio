package com.madrid.presentation.screens.homeScreen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.MovioPager
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.screens.homeScreen.component.TrendingLayout
import com.madrid.presentation.viewModel.homeViewModel.CategoryUiState
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState

@Composable
fun AllMediaLayout() {
    val fakeMediaList = getFakeMedia()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            MovioPager(
                medias = fakeMediaList,
            )
        }
        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.top_rating),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = fakeMediaList,
                onSeeAllClick = {},
                onMediaClick = {},
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            TrendingLayout(headerModifier = Modifier.padding(horizontal = 16.dp))
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.free_to_watch),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = fakeMediaList,
                onSeeAllClick = {},
                onMediaClick = {},
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.upcoming),
                secondaryTextForCustomTextTitle = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitle = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = fakeMediaList,
                onSeeAllClick = {},
                onMediaClick = {},
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            CustomTextTitle(
                primaryText = stringResource(com.madrid.presentation.R.string.more_recommended),
                secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
                endIcon = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        itemsIndexed(fakeMediaList) { index, media ->
            var endPaddingValue = 0
            var startPaddingValue = 0

            if (index % 2 == 0)
                startPaddingValue = 16
            else
                endPaddingValue = 16
            MovioVerticalCard(
                description = media.title,
                movieImage = media.imageUrl,
                rate = media.rating,
                height = 220.dp,
                onClick = {},
                modifier = Modifier.padding(start = startPaddingValue.dp, end = endPaddingValue.dp)
            )
        }
    }
}


fun getFakeMedia(): List<MediaUiState> {
    return listOf(
        MediaUiState(
            id = "1",
            mediaType = MediaType.MOVIE,
            title = "Inception",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg\n",
            rating = "8.8",
            category = listOf(
                CategoryUiState(name = "Sci-Fi")
            ),
        ),
        MediaUiState(
            id = "2",
            mediaType = MediaType.TV_SHOW,
            title = "Breaking Bad",
            imageUrl = "https://image.tmdb.org/t/p/w500/53dsJ3oEnBhTBVMigWJ9tkA5bzJ.jpg",
            rating = "9.5",
            category = listOf(
                CategoryUiState(name = "Drama")
            ),
        ),
        MediaUiState(
            id = "3",
            mediaType = MediaType.MOVIE,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/x9HeaagUAyyGl1fQ6exQcpELBxP.jpg",
            rating = "9.0",
            category = listOf(
                CategoryUiState(name = "Action")
            ),
        ),
        MediaUiState(
            id = "4",
            mediaType = MediaType.MOVIE,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/aFRDH3P7TX61FVGpaLhKr6QiOC1.jpg",
            rating = "9.0",
            category = listOf(
                CategoryUiState(name = "Action")
            ),
        )
    )
}