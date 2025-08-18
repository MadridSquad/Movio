package com.madrid.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

@Composable
fun LoadingSearchCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(Theme.color.surfaces.surface),
    ) {
        Box(
            modifier = modifier
                .width(101.dp)
                .height(136.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Theme.color.surfaces.surfaceContainer),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = modifier
                .width(101.dp)
                .height(15.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Theme.color.surfaces.surfaceContainer),
        )
    }
}