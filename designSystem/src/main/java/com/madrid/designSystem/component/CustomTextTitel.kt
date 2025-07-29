package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun CustomTextTitel(
    primaryText: String,
    modifier: Modifier = Modifier,
    secondaryText: String? = null,
    endIcon: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovioText(
            text = primaryText,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.title.mediumMedium16,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.weight(1f))
        if (secondaryText != null || endIcon != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onSeeAllClick?.invoke() }
            ) {
                secondaryText?.let {
                    MovioText(
                        text = it,
                        color = Theme.color.surfaces.onSurfaceVariant,
                        textStyle = Theme.textStyle.label.smallRegular14,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                endIcon?.let {
                    MovioIcon(
                        painter = it,
                        contentDescription = "See all",
                        tint = Theme.color.surfaces.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextTitelPreview() {
    MovioTheme {
        CustomTextTitel(
            primaryText = "Top Rating",
            secondaryText = "See all",
        )
    }
}