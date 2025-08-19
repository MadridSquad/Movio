package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme


@Composable
fun LoginHeader(
    modifier: Modifier= Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {


        LogoWithBackground()
        MovioText(
            text = stringResource(com.madrid.presentation.R.string.welcome_back),
            textStyle = Theme.textStyle.display.mediumMedium20,
            color = Theme.color.surfaces.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun LogoWithBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Theme.color.gradients.iconGradient, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Theme.color.surfaces.surface, shape = CircleShape)
        )
        Image(
            painter = painterResource(R.drawable.library_main_icon),
            contentDescription = "Movio Logo",
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginHeaderPreview() {
    MovioTheme {
        LoginHeader()
    }
}