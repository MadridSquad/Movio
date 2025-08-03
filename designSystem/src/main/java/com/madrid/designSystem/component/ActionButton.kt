package com.madrid.designSystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    text: String,
    onClick: () -> Unit,
    color: Color = Theme.color.brand.primary,
    icon: Painter = painterResource(R.drawable.loading),
) {
    MovioButton(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        color = color,
        onClick = onClick
    ) {
        if (isLoading) {
            MovioIcon(
                painter = icon,
                contentDescription = "loading icon",
                tint = Theme.color.brand.onPrimary,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        MovioText(
            modifier = Modifier.padding(vertical = 16.dp),
            text = text,
            color = Theme.color.brand.onPrimary,
            textStyle = Theme.textStyle.label.mediumMedium14,
        )
    }
}
