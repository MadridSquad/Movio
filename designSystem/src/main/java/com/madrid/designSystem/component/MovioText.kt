package com.madrid.designSystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MovioText(
    text: String,
    modifier: Modifier = Modifier,
    brush: Brush? = null,
    color: Color = Color.Unspecified,
    textStyle: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        textAlign = textAlign,
        style = if (brush != null) {
            textStyle.copy(brush = brush)
        } else {
            textStyle.copy(color = color)
        },
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow
    )
}
