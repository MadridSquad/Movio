package com.madrid.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.OnBoardingPager

@Composable
fun OnBoardingScreen() {
    Column(Modifier
        .fillMaxSize()
        .background(Theme.color.surfaces.surface)) {
        Image(
            painter = painterResource(R.drawable.onboarding),
            contentDescription = "onboarding",
            modifier = Modifier
                .padding(top = 142.dp)
                .fillMaxWidth()
                .height(401.dp)
        )
        Spacer(Modifier.height(40.dp))
        Column(Modifier.padding(horizontal = 16.dp)) {
            Row() {
                MovioText(
                    text = "Open ",
                    textStyle = Theme.textStyle.display.mediumMedium20,
                    color = Theme.color.surfaces.onSurface
                )
                MovioText(
                    text = "Movio",
                    textStyle = Theme.textStyle.headline.largeBold18,
                    brush = Brush.verticalGradient(
                        colors = listOf( Color(0xFFEBE6FE),Color(0xFF7C5DF6))
                    )
                )
            }
            MovioText(
                text = "and let the noise fade away",
                textStyle = Theme.textStyle.display.mediumMedium20,
                color = Theme.color.surfaces.onSurface
            )
            Spacer(Modifier.height(12.dp))
            MovioText(
                text = "Swipe, pick, dive in… and be the star of the scene.",
                textStyle = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceContainer
            )

            Spacer(Modifier.weight(1f))
            Column(Modifier.fillMaxWidth()) {
                OnBoardingPager(modifier = Modifier.padding(bottom = 80.dp).align(Alignment.CenterHorizontally))

            }

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOnBoardingScreen(){
    MovioTheme {
        OnBoardingScreen()
    }
}