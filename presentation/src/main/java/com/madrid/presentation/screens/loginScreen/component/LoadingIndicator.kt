package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    val barColor = Theme.color.brand.onPrimary
 val backgroundColor = Color.Unspecified

    val infiniteTransition = rememberInfiniteTransition(label = "LoadingIndicatorTransition")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ),
        label = "LoadingIndicatorRotation"
    )

    Canvas(modifier = modifier.size(20.dp)) {
        drawRect(color = backgroundColor, size = size)

        val numberOfBars = 8
        val barWidth = 2.dp.toPx()
        val barHeight = 6.dp.toPx()
        val radius = size.minDimension / 2 - barHeight / 2

        rotate(degrees = rotation) {
            for (i in 0 until numberOfBars) {
                rotate(degrees = i * (360f / numberOfBars)) {
                    drawRoundRect(
                        color = barColor,
                        topLeft = Offset(center.x - barWidth / 2, center.y - radius - barHeight / 2),
                        size = Size(barWidth, barHeight),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}
