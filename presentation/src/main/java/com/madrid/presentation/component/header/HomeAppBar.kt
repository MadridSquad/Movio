package com.madrid.presentation.component.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.ProfilePicture
import com.madrid.designSystem.theme.Theme


@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.library_main_icon),
            contentDescription = "app icon"
        )
        Spacer(Modifier.width(8.dp))
        MovioText(
            text = "Movio",
            textStyle = Theme.textStyle.headline.largeBold18,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFEBE6FE), Color(0xFF7C5DF6))
            )
        )
        Spacer(Modifier.weight(1f))
        ProfilePicture(
            image = R.drawable.profile_pic_holder,
            size = 24.dp,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeAppBar() {
    HomeAppBar()
}