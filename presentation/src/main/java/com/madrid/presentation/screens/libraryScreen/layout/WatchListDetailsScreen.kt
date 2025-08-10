package com.madrid.presentation.screens.libraryScreen.layout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioHorizontalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.libraryViewModel.layout.WatchListDetailsState
import com.madrid.presentation.viewModel.libraryViewModel.layout.WatchListDetailsViewModel

@Composable
fun WatchListDetailsScreen(
    watchListDetailsViewModel: WatchListDetailsViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val state = watchListDetailsViewModel.state.collectAsStateWithLifecycle().value

    WatchListDetailsScreenContent(
        state = state,
        onClickBackIcon = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun WatchListDetailsScreenContent(
    state : WatchListDetailsState,
    onClickBackIcon: () -> Unit,

    ){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp , horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioIcon(
                    modifier = Modifier.clickable {onClickBackIcon() },
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = null,
                    tint = Theme.color.surfaces.onSurface
                )
                Spacer(Modifier.width(8.dp))
                MovioText(
                    text = state.headerTitle,
                    color = Theme.color.surfaces.onSurface,
                    textStyle = Theme.textStyle.headline.largeBold16,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }        }
        items(
            state.watchList.size
        ) {index->
            val watchList = state.watchList[index]
            MovioHorizontalCard(
                movieTitle = watchList.title,
                movieRate = watchList.rating,
                movieCategory = watchList.category.first().name,
                movieImageUrl = watchList.imageUrl,
                width = 86.dp,
                height = 120.dp,
            )
        }
    }


}