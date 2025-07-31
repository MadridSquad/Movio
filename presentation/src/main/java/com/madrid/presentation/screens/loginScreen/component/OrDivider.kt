package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun OrDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Theme.color.surfaces.onSurfaceVariant.copy(alpha = 0.3f)
        )
        MovioText(
            text = "or",
            textStyle = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Theme.color.surfaces.onSurfaceVariant.copy(alpha = 0.3f)
        )
    }
}