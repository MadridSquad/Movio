package com.madrid.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun MovioIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color? = null,
    brush: Brush? = null
) {
    when {
        brush != null -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = modifier
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(brush = brush, blendMode = BlendMode.SrcIn)
                        }
                    }
            )
        }

        tint != null -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = modifier,
                colorFilter = ColorFilter.tint(tint)
            )
        }

        else -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
    }
}