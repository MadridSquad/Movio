package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun CustomTextTitle(
    primaryText: String,
    modifier: Modifier = Modifier,
    startIcon: Painter? = null,
    secondaryText: String? = null,
    endIcon: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    isListEmpty : Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (startIcon != null ){
            MovioIcon(
                painter = startIcon,
                contentDescription = "Primary Text Icon",
                tint =Theme.color.surfaces.onSurface,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
            )
        }
        Column (
        ){
            MovioText(
                text = primaryText,
                color = Theme.color.surfaces.onSurface,
                textStyle = Theme.textStyle.title.mediumMedium16,
                modifier = Modifier
            )

        }

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
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextTitlePreview() {
    MovioTheme {
        CustomTextTitle(
            primaryText = "Top Rating",
            secondaryText = "See all",
        )
    }
}