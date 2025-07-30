package com.madrid.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun TopAppBar(
    text: String?,
    modifier: Modifier = Modifier,
    firstIcon: Int? = R.drawable.arrow_left,
    secondIcon: Int? = R.drawable.share_arrow,
    thirdIcon: Int? = R.drawable.outline_heart,
    onFirstIconClick: () -> Unit = {},
    onSecondIconClick: () -> Unit = {},
    onThirdIconClick: () -> Unit = {},
    isFavorite: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        firstIcon?.let { iconRes ->
            MovioIcon(
                painter = painterResource(id = iconRes),
                contentDescription = "back_button",
                tint = Theme.color.surfaces.onSurface,
                modifier = Modifier.clickable { onFirstIconClick() }
            )
        } ?: Box(modifier = Modifier.size(24.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (text != null) {
                MovioText(
                    text = text,
                    textStyle = Theme.textStyle.headline.largeBold18,
                    color = Theme.color.surfaces.onSurface
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            secondIcon?.let { iconRes ->
                MovioIcon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "share_button",
                    tint = Theme.color.surfaces.onSurface,
                    modifier = Modifier.clickable { onSecondIconClick() }
                )
            }

            thirdIcon?.let { iconRes ->
                MovioIcon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.bold_heart
                        else R.drawable.outline_heart
                    ),
                    contentDescription = "favorite_button",
                    tint = if (isFavorite) Theme.color.system.error
                    else Theme.color.surfaces.onSurface,
                    modifier = Modifier.clickable { onThirdIconClick() }
                )
            }
        }
    }
}