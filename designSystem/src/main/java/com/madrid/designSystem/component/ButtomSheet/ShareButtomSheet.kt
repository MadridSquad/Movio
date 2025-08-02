package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

data class ShareOptionData(
    val icon: Int,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun ShareOption(
    data: ShareOptionData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(74.dp)
            .height(71.dp)
            .clickable(onClick = data.onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Theme.color.surfaces.surfaceContainer),
            contentAlignment = Alignment.Center
        ) {
            MovioIcon(
                painter = painterResource(id = data.icon),
                contentDescription = data.label,
                tint = Theme.color.surfaces.onSurface
            )
        }
        MovioText(
            text = data.label,
            textStyle = MaterialTheme.typography.labelMedium,
            color = Theme.color.surfaces.onSurface
        )
    }
}

@Composable
fun ShareViaBottomSheetContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onShareOptionClick: (String) -> Unit
) {
    val shareOptions = listOf(
        ShareOptionData(
            R.drawable.outline_link_minimalistic,
            "Copy link"
        ) { onShareOptionClick("Copy link") },
        ShareOptionData(
            R.drawable.facebook,
            "Facebook"
        ) { onShareOptionClick("Facebook") },
        ShareOptionData(
            R.drawable.social_icon,
            "X"
        ) { onShareOptionClick("X") }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Theme.color.surfaces.surface)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Drag handle
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Theme.color.surfaces.onSurfaceContainer.copy(alpha = 0.3f))
        )

        MovioText(
            text = "Share via",
            textStyle = MaterialTheme.typography.titleMedium,
            color = Theme.color.surfaces.onSurface
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top
        ) {
            shareOptions.forEach { option ->
                ShareOption(data = option)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShareViaBottomSheetContentPreview() {
    ShareViaBottomSheetContent(
        onShareOptionClick = { option -> println("Selected: $option") }
    )
}