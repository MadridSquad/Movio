package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(60.dp, 66.dp),
            contentAlignment = Alignment.Center
        ) {
            MovioIcon(
                painter = painterResource(R.drawable.library_main_icon),
                contentDescription = "App Icon",
                modifier = Modifier.size(60.dp , 66.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MovioText(
                modifier = Modifier.height(19.dp),
                text =  stringResource(id = R.string.conform_log_out),
                textStyle = Theme.textStyle.title.mediumMedium16,
                color = Theme.color.surfaces.onSurface,
                textAlign = TextAlign.Center
            )
            MovioText(
                text = stringResource(id = R.string.log_out),
                textStyle = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.height(35.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        MovioButton(
            onClick = onLogoutConfirm,
            modifier = Modifier
                .width(328.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Theme.color.brand.primary),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                MovioText(
                    text = stringResource(id = R.string.logout),
                    textStyle = Theme.textStyle.label.mediumMedium14,
                    color = Color.White
                )
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutConfirmationBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
    ) {
        // Test button to open the bottom sheet
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MovioButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .background(Theme.color.brand.primary)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                MovioText(
                    text = "Open Logout Bottom Sheet",
                    textStyle = Theme.textStyle.body.mediumMedium12,
                    color = Color.White
                )
            }
        }

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