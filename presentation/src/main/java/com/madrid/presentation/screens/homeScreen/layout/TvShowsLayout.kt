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
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.presentation.component.CustomHorizontalCard
import com.madrid.presentation.component.MovioPager
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTvShowType

@Composable
fun TvShowsLayout(){
    val fakeMediaList = getFakeMedia()
    val navController = LocalNavController.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            MovioPager(
                movies = fakeMediaList,
            )
        }
        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.top_rating),
                secondaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitel = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = fakeMediaList,
                onSeeAllClick = { navController.navigate(Destinations.SeeAllTvShowsScreen(
                    SeeAllTvShowType.TOP_RATING))},
                onMediaClick = {},
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.airing_today),
                secondaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitel = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = fakeMediaList,
                onSeeAllClick = { navController.navigate(Destinations.SeeAllTvShowsScreen(
                    SeeAllTvShowType.AIRING_TODAY))},
                onMediaClick = {},
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            CustomHorizontalCard(
                primaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.on_tv),
                secondaryTextForCustomTextTitel = stringResource(com.madrid.presentation.R.string.see_all),
                endIconForCustomTextTitel = painterResource(R.drawable.outline_alt_arrow_left),
                listOfMedia = fakeMediaList,
                onSeeAllClick = { navController.navigate(Destinations.SeeAllTvShowsScreen(
                    SeeAllTvShowType.ON_TV))},
                onMediaClick = {},
                headerModifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)) }

        item(span = { GridItemSpan(2) }) {
            CustomTextTitel(
                primaryText = stringResource(com.madrid.presentation.R.string.more_recommended),
                secondaryText = "See all",
                endIcon = painterResource(R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { navController.navigate(Destinations.SeeAllTvShowsScreen(
                    SeeAllTvShowType.MORE_RECOMMENDED))},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        itemsIndexed(fakeMediaList) { index , media ->
            var endPaddingValue = 0
            var startPaddingValue = 0

            if(index % 2 ==0)
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