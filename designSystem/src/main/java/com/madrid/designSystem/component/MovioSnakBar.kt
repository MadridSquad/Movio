package com.madrid.designSystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun MovioSnakBar(
    message: String,
    modifier: Modifier = Modifier,
    duration: ToastDuration = ToastDuration.SHORT,
    icon: Painter? = painterResource(id = R.drawable.danger),
    onDismiss: () -> Unit
) {
    val visible = message.isNotEmpty()

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "Toast"
    )

    if (alpha > 0f) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
                .background(
                    color = Theme.color.surfaces.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            icon?.let {
                MovioIcon(
                    painter = it,
                    contentDescription = "Toast Icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            MovioText(
                text = message,
                textStyle = Theme.textStyle.body.mediumMedium14,
                color = Theme.color.surfaces.onSurface
            )
        }

        LaunchedEffect(message, duration) {
            delay(duration.timeMillis)
            onDismiss()
        }
    }
}

enum class ToastDuration(val timeMillis: Long) {
    SHORT(2000L),
    LONG(3500L)
}

@Preview(showBackground = true)
@Composable
private fun MovioToastPreview() {

    MovioSnakBar(
        message = "Something went wrong",
        onDismiss = {}
    )
}

