package com.madrid.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun showSnackBar(
    scope: CoroutineScope,
    hostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
) {
    scope.launch {
        hostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
fun CustomSnackBarHost(
    snackBarHostState: SnackbarHostState,
    imagePainter: Painter,
    onUndoClick: (() -> Unit)? = null
) {
    SnackbarHost(
        hostState = snackBarHostState,
        snackbar = { snackBarData ->
            Snackbar(
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
                containerColor = Theme.color.surfaces.surfaceContainer,
                contentColor = Theme.color.surfaces.onSurface,
                shape = RoundedCornerShape(8.dp),
                action = {
                    snackBarData.visuals.actionLabel?.let { label ->
                        TextButton(
                            onClick = {
                                snackBarData.dismiss()
                                onUndoClick?.invoke()
                            }
                        ) {
                            MovioText(
                                text = label,
                                textStyle = Theme.textStyle.label.mediumMedium14,
                                color = Theme.color.brand.primary
                            )
                        }
                    }
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MovioIcon(
                        painter = imagePainter,
                        contentDescription = "",
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFB7A4FB), Color(0xFF724CF8))
                        )
                    )
                    MovioText(
                        text = snackBarData.visuals.message,
                        color = Theme.color.surfaces.onSurface,
                        textStyle = Theme.textStyle.label.smallRegular14
                    )
                }

            }
        }
    )
}

@Preview
@Composable
fun CustomSnackBarHostPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    Button(
        onClick = {
            showSnackBar(
                hostState = snackbarHostState,
                scope = CoroutineScope(Dispatchers.Main),
                message = "Successfully added to your collection.",
                actionLabel = "sss"
            )
        },
        modifier = Modifier.padding(16.dp)
    ) {
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        CustomSnackBarHost(
            snackBarHostState = snackbarHostState,
            imagePainter = painterResource(R.drawable.archive_tick),
            onUndoClick = {},
        )
    }
}