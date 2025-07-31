package com.madrid.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

@Composable
fun MovioButton(
    modifier: Modifier = Modifier,
    color: Color = Theme.color.brand.primary,
    backgroundColor: Color? = color,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val buttonModifier = if (backgroundColor != null) {
        modifier
            .clip(RoundedCornerShape(32.dp))
            .background(backgroundColor)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    } else {
        modifier
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp)
    }

    Box(
        modifier = buttonModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}
@Composable
fun MovioTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Theme.color.brand.primary,
    textStyle: androidx.compose.ui.text.TextStyle = Theme.textStyle.label.smallRegular14
) {
    MovioText(
        text = text,
        color = textColor,
        textStyle = textStyle,
        modifier = modifier.clickable { onClick() }
    )
}