package com.madrid.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

@Composable
fun MovioCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    containerColor: Color = Theme.color.surfaces.onSurfaceVariant,
    defaultElevationSize: Dp = 0.dp,
    border: BorderStroke? = null,
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = defaultElevationSize),
        border = border,
    ) {

    }
}