package com.madrid.presentation.screens.addtolist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun SuccessNotificationRow(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    message: String = "Successfully added to your collection.",
    onAction: () -> Unit = {},
    actionText: String? = null,
    onDismiss: () -> Unit = {},
    icon: Painter = painterResource(id = com.madrid.designSystem.R.drawable.archive_tick),
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(3000)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(200)
        ) + fadeOut(animationSpec = tween(200)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Color(0xFF1A162F)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MovioIcon(
                modifier = Modifier.size(24.dp),
                painter = icon,
                contentDescription = "Success",
            )

            // Success message
            MovioText(
                modifier = Modifier.weight(1f),
                text = message,
                color = Color.White,
                textStyle = Theme.textStyle.label.mediumMedium14
            )
            if (actionText != null) {
                MovioText(
                    modifier = Modifier.clickable(onClick = onAction),
                    text = actionText,
                    color = Theme.color.brand.primary,
                    textStyle = Theme.textStyle.label.mediumMedium14
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessNotificationRow() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        SuccessNotificationRow(
            isVisible = true,
            message = "Successfully added to your collection."
        )

        SuccessNotificationRow(
            isVisible = true,
            message = "Item has been deleted",
            actionText = "Undo",
            onAction = { /* Handle undo action */ },
        )
    }
}