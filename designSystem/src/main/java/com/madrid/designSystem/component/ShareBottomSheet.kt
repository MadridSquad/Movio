package com.madrid.designSystem.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun ShareBottomSheetContent(
    onCopyLink: () -> Unit,
    onShareFacebook: () -> Unit,
    onShareX: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        MovioText(
            text = stringResource(R.string.share_via),
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = Theme.color.surfaces.onSurface,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            ShareOptionItem(
                icon = R.drawable.copy_link,
                label = stringResource(R.string.copy_link),
                onClick = onCopyLink
            )
            ShareOptionItem(
                icon = R.drawable.facebook,
                label = stringResource(R.string.facebook),
                onClick = onShareFacebook
            )
            ShareOptionItem(
                icon = R.drawable.twitter,
                label = stringResource(R.string.x),
                onClick = onShareX
            )
        }
    }
}

@Composable
fun ShareOptionItem(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .height(71.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(shape = CircleShape)
                .background(Theme.color.surfaces.surfaceContainer)

        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Theme.color.surfaces.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
        MovioText(
            text = label,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.label.smallRegular12,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShareBottomSheetContentPreview() {
    MovioTheme {
        ShareBottomSheetContent({}, {}, {})
    }
}