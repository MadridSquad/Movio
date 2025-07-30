package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.designSystem.theme.MovioTheme

@Composable
fun TextWithReadMore(
    description: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 5
) {
    var isExpanded by remember { mutableStateOf(false) }
    val readMoreTag = "READ_MORE"
    val readLessTag = "READ_LESS"

    val annotatedText = buildAnnotatedString {
        if (isExpanded) {
            append(description)
            append(" ")
            pushStringAnnotation(tag = readLessTag, annotation = "read_less")
            withStyle(
                SpanStyle(
                    color = Theme.color.surfaces.onSurfaceVariant,
                    fontSize = Theme.textStyle.label.mediumMedium14.fontSize,
                    fontWeight = FontWeight.Medium
                )
            ) {
                append(stringResource(R.string.read_less))
            }
            pop()
        } else {
            val preview = description.take(150)
            append(preview)
            if (description.length > 150) {
                append("... ")
                pushStringAnnotation(tag = readMoreTag, annotation = "read_more")
                withStyle(
                    SpanStyle(
                        color = Theme.color.surfaces.onSurfaceVariant,
                        fontSize = Theme.textStyle.label.mediumMedium14.fontSize,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(R.string.read_more))
                }
                pop()
            }
        }
    }

    ClickableText(
        text = annotatedText,
        modifier = modifier.fillMaxWidth(),
        style = Theme.textStyle.body.smallRegular16.copy(color = Theme.color.surfaces.onSurface),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = readMoreTag, start = offset, end = offset)
                .firstOrNull()?.let {
                    isExpanded = true
                }
            annotatedText.getStringAnnotations(tag = readLessTag, start = offset, end = offset)
                .firstOrNull()?.let {
                    isExpanded = false
                }
        }
    )
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