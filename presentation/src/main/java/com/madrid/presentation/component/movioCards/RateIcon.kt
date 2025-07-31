package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R.string

@Composable
fun RateIcon(
    rate: String,
    modifier: Modifier = Modifier,
    icon: Painter = painterResource(R.drawable.bold_star),
) {
    Box(
        modifier = modifier
            .padding(top = 4.dp, end= 4.dp)
            .height(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovioIcon(
                painter = icon,
                contentDescription = stringResource(
                    string.stars_icon_rate
                ),
                tint = Theme.color.system.warning,
                modifier = Modifier.size(16.dp)
            )
            MovioText(
                text = rate,
                color = Theme.color.system.onWarning,
                textStyle = Theme.textStyle.label.smallRegular12,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
private fun RateIconPreview() {
    RateIcon(
        icon = painterResource(id = R.drawable.bold_star),
        rate = "10",
    )
}