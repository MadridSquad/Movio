package com.madrid.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioHorizontalCard
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteCard(
    title: String,
    movieRate: String,
    movieCategory: String,
    movieImageUrl: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val maxSwipeDistance = 120f
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.brand.primary)
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            MovioIcon(
                painter = painterResource(R.drawable.icon_delete),
                contentDescription = stringResource(R.string.delete),
                tint = Theme.color.brand.onPrimary,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onDelete()
                    }
            )
        }
        val scope = rememberCoroutineScope()
        val offsetXAnim = remember { Animatable(0f) }
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetXAnim.value.roundToInt(), 0) }
                .graphicsLayer { scaleX = if (isRtl) -1f else 1f }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val down = awaitPointerEvent().changes.firstOrNull() ?: continue
                            val drag = awaitDragOrCancellation(down.id) ?: continue

                            var totalDragX = 0f
                            var totalDragY = 0f

                            while (drag.pressed) {
                                val event = awaitPointerEvent()
                                val change = event.changes.firstOrNull() ?: break
                                val dragAmount = change.positionChange()

                                totalDragX += dragAmount.x
                                totalDragY += dragAmount.y

                                if (abs(totalDragX) > abs(totalDragY)) {
                                    change.consume()
                                    scope.launch {
                                        offsetXAnim.snapTo(
                                            (offsetXAnim.value + dragAmount.x)
                                                .coerceIn(-maxSwipeDistance, 0f)
                                        )
                                    }
                                }
                                if (!change.pressed) break
                            }
                            scope.launch {
                                if (offsetXAnim.value < -maxSwipeDistance / 2) {
                                    offsetXAnim.animateTo(-maxSwipeDistance, tween(200))
                                } else {
                                    offsetXAnim.animateTo(0f, tween(200))
                                }
                            }
                        }
                    }
                }
        ) {
            MovioHorizontalCard(
                movieTitle = title,
                movieRate = movieRate,
                movieCategory = movieCategory,
                movieImageUrl = movieImageUrl,
                height = 100.dp,
                onClick = onClick,
                modifier = Modifier.graphicsLayer {
                    scaleX = if (isRtl) -1f else 1f
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SwipeToDeleteCardPreview() {
    MovioTheme {
        SwipeToDeleteCard(
            title = "Spider-Man: No Way Home",
            movieRate = "8.9",
            movieCategory = "Action",
            movieImageUrl = "https://via.placeholder.com/150",
            onDelete = {  },
            onClick = {  }
        )
    }
}