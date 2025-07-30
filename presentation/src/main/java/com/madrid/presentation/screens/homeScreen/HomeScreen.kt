package com.madrid.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.screens.homeScreen.component.TrendingLayout

@Composable
fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Theme.color.surfaces.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {

        // SliderLayout()
        TrendingLayout()

        // TopRatedLayout()
        // FreeToWatchLayout()
        // UpcomingLayout()
        // More RecommendedLayout()

    }
}