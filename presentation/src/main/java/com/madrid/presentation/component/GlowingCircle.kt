package com.madrid.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.modifier.dropShadow

@Composable
fun GlowingCircle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(230.dp)
            .dropShadow(
                shape = CircleShape,
                color = Color(0x33734EF8),
                blur = 265.dp,
                offsetX = 0.dp,
                offsetY = 0.dp,
                alpha = 0.10f
            ), content = {})
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun GlowCirclePreview() {
    MovioTheme(
        isDarkTheme = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaces.surface)

        ) {

            GlowingCircle(modifier = Modifier.align(alignment = Alignment.TopEnd))
        }
    }
}
