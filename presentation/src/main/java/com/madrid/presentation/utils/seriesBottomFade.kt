package com.madrid.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

@Composable
fun Modifier.seriesBottomFade(): Modifier {
    return this
        .fillMaxWidth()
        .height(30.dp)
        .offset(y = 342.dp)
        .background(
            Brush.verticalGradient(
                colors = listOf(Color.Transparent, Theme.color.surfaces.surface)
            )
        )
}