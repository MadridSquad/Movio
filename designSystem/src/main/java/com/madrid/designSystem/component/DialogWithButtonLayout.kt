package com.madrid.designSystem.component

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun DialogWithButtonLayout(
    title: String,
    description: String,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    topBarTitle: String = "",
    buttonText: String = "",
    onClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        MovioText(
            text = topBarTitle,
            textStyle = Theme.textStyle.headline.largeBold16,
            color = Theme.color.surfaces.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        EmptySearchLayout(
            title = title,
            description = description,
            image = image,
            imageSize = 88,
            modifier = Modifier
                .fillMaxWidth()
        )
        MovioButton(
            onClick = onClick,
            color = Theme.color.brand.primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            MovioText(
                text = buttonText,
                textStyle = Theme.textStyle.label.mediumMedium14,
                color = Theme.color.brand.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun DialogWithButtonLayoutPreview() {
    DialogWithButtonLayout(
        topBarTitle = "Library",
        title = "Log in to unlock your personal library",
        description = "Access your watch history, favorites, and watchlist  all in one place.",
        image = R.drawable.library_main_icon,
        buttonText = "Login",
        onClick = {}
    )
}