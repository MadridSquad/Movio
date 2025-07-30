package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun TextWithReadMore(
    description: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTextOverflow by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        MovioText(
            text = if (isExpanded) description else {
                if (description.length > 150) {
                    description.take(150) + "..."
                } else {
                    description
                }
            },
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.body.smallRegular16,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines
        )

        if (isTextOverflow || description.length > 150) {
            MovioText(
                text = if (isExpanded) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                color = Theme.color.surfaces.onSurfaceVariant,
                textStyle = Theme.textStyle.label.mediumMedium14,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDescriptionPreview() {
    MovioTheme {
        TextWithReadMore(
            description = "Taking place during the events of John Wick: Chapter 3 – Parabellum, Eve Macarro begins her training in the assassin traditions of the Ruska Roma. Derek Kolstad :Characters , Len Wiseman:Director , Shay Hatten"
        )
    }
} 