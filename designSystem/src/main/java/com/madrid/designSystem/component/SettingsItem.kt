package com.madrid.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    text: String = "",
    clickable: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .requiredHeight(height = 48.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "$title Icon",
            tint = Theme.color.surfaces.onSurface,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
        )
        Text(
            text = title,
            color = Theme.color.surfaces.onSurface,
            style = Theme.textStyle.title.mediumMedium16,
            modifier = Modifier
                .weight(1f)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .padding(horizontal = 8.dp)
                .clickable(
                    enabled = clickable,
                    onClick = onClick
                )
        ) {
            Text(
                text = text,
                color = Theme.color.surfaces.onSurfaceVariant,
                style = Theme.textStyle.label.smallRegular14,
                modifier = Modifier
            )
            if (clickable) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_alt_arrow_left),
                    contentDescription = "on $title Click Arrow",
                    tint = Theme.color.surfaces.onSurfaceVariant,
                    modifier = Modifier
                        .requiredSize(size = 16.dp)
                )
            }
        }


    }
}

@Preview
@Composable
private fun SettingsItemPreview() {
    SettingsItem(
        icon = R.drawable.outline_star,
        title = "Settings",
        text = "Dark",
        clickable = false,
        onClick = {}
    )
}