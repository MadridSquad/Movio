package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun LogoutConfirmationBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onLogoutConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovioBottomSheet(
        show = isVisible,
        onDismiss = onDismiss,
        containerColor = Theme.color.surfaces.surface
    ) {
        LogoutConfirmationContent(
            onLogoutConfirm = onLogoutConfirm,
            modifier = modifier
        )
    }
}

@Composable
private fun LogoutConfirmationContent(
    onLogoutConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF8B5CF6).copy(alpha = 0.2f),
                            Color(0xFF8B5CF6).copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 80f
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF8B5CF6),
                                Color(0xFF7C3AED)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                MovioIcon(
                    painter = painterResource(R.drawable.library_main_icon),
                    contentDescription = "Logout",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MovioText(
                text = "Confirm Logout",
                textStyle = Theme.textStyle.title.mediumMedium16,
                color = Theme.color.surfaces.onSurface,
                textAlign = TextAlign.Center
            )

            MovioText(
                text = "You'll lose access to your library, favorites, and history until you sign back in.",
                textStyle = Theme.textStyle.title.mediumMedium16,
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        MovioButton(
            onClick = onLogoutConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            MovioText(
                text = "Logout",
                textStyle = Theme.textStyle.body.mediumMedium12,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutConfirmationBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .background(Color(0xFF1A1B23))
    ) {
        LogoutConfirmationBottomSheet(
            isVisible = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            onLogoutConfirm = {
                println("Logout confirmed")
                showBottomSheet = false
            }
        )
    }
}