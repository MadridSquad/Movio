package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioSeasonCard
import com.madrid.presentation.viewModel.detailsViewModel.SeasonUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeeAllType
import com.madrid.presentation.viewModel.shared.parser.formatFullDateKtx
import com.madrid.presentation.viewModel.shared.parser.formatYearKtx

@Composable
fun CurrentSeasonsSection(
    onSeeAllClick: () -> Unit,
    seasons: List<SeasonUiState>,
    onCurrentSeasonCardClick: (Int) -> Unit
) {
    Log.d("tag series details lol","in current : $seasons")
    CurrentSeasonsTitle(onSeeAllClick = onSeeAllClick)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(seasons) { _, season ->
            MovioSeasonCard(
                movieTitle = season.title,
                movieImage = season.imageUrl,
                movieRate = season.rate,
                totalNumberOfEpisodes = season.numberOfEpisodes.toString(),
                onClick = { onCurrentSeasonCardClick(season.seasonNumber) },
                yearOfPublish = season.productionDate.formatYearKtx(),
                currentSeason = season.seasonNumber.toString(),
                timeOfPublish = season.productionDate.formatFullDateKtx(),
                modifier = Modifier.width(250.dp)
            )
        }
    }
}

@Composable
fun CurrentSeasonsTitle(
    onSeeAllClick: () -> Unit,
) {
    CustomTextTitle(
        primaryText = stringResource(SeeAllType.Season.stringResId),
        secondaryText = stringResource(R.string.see_all),
        endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
        onSeeAllClick = { onSeeAllClick() },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 12.dp)
    )
}
