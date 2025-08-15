package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme

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

@Composable
fun MovioRecentSearchText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
     textStyle: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    startIcon: Painter,
    endIcon: Painter,
    onEndIconClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        MovioIcon(
            painter = startIcon,
            contentDescription = "start icon",
            tint = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)
        )

        Text(
            text = text,
            textAlign = textAlign,
            style = textStyle,
            modifier = modifier,
            maxLines = maxLines,
            overflow = overflow
        )


        MovioIcon(
            painter = endIcon,
            contentDescription = "end icon",
            tint = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier
                .size(24.dp)
                .clickable { onEndIconClick() }
        )

    }
}