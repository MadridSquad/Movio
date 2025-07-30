package com.madrid.presentation.screens.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.HeaderSectionBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.header.HomeAppBar
import com.madrid.presentation.screens.homeScreen.component.TrendingLayout
import com.madrid.presentation.screens.homeScreen.layout.AllMediaLayout
import com.madrid.presentation.screens.homeScreen.layout.CategoriesLayout
import com.madrid.presentation.screens.homeScreen.layout.MoviesLayout
import com.madrid.presentation.screens.homeScreen.layout.TvShowsLayout

@Composable
fun HomeScreen() {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent(){
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .padding(top = 32.dp),
    ) {

        HomeAppBar(modifier = Modifier.padding(horizontal = 16.dp))
        HeaderSectionBar(
            tabs = listOf(
                stringResource(R.string.all),
                stringResource(R.string.Movies),
                stringResource(R.string.TV_Shows),
                stringResource(R.string.Categories),
            ),
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index ->
                selectedTabIndex = index
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LayoutContent(HomeTab.entries[selectedTabIndex])

    }
}


@Composable
private fun LayoutContent(selectedTab: HomeTab){
    when (selectedTab) {
        HomeTab.ALL -> AllMediaLayout()
        HomeTab.MOVIES -> MoviesLayout()
        HomeTab.TV_SHOWS -> TvShowsLayout()
        HomeTab.CATEGORIES -> CategoriesLayout()
    }
}

enum class HomeTab {
    ALL,
    MOVIES,
    TV_SHOWS,
    CATEGORIES
}