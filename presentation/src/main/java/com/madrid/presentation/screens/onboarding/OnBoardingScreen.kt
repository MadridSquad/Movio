package com.madrid.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.OnBoardingPager
import com.madrid.presentation.screens.loginScreen.component.LogoWithBackground

@Composable
fun OnBoardingScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(R.drawable.onboarding_top),
            contentDescription = "onboarding",
            modifier = Modifier
                .padding(top = 142.dp)
                .fillMaxWidth()
                .height(268.dp)
                .align(Alignment.CenterHorizontally)
        )
        Image(
            painter = painterResource(R.drawable.onboarding_mid),
            contentDescription = "onboarding",
            modifier = Modifier
                .width(218.dp)
                .height(114.dp)
                .align(Alignment.CenterHorizontally)
        )
        Box(modifier = Modifier.align(Alignment.CenterHorizontally).offset(y = -8.dp)){
            LogoWithBackground()
        }

        Spacer(Modifier.height(40.dp))
        Column(Modifier.padding(horizontal = 16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovioText(
                    text = stringResource(R.string.open),
                    textStyle = Theme.textStyle.display.mediumMedium20,
                    color = Theme.color.surfaces.onSurface
                )
                Spacer(Modifier.width(5.dp))
                MovioText(
                    text = stringResource(R.string.movio),
                    textStyle = Theme.textStyle.display.largeBold24,
                    brush = Brush.verticalGradient(
                        colors = listOf(Theme.color.brand.onPrimary, Color(0xFF7C5DF6))
                    )
                )
            }
            MovioText(
                text = stringResource(R.string.and_let_the_noise_fade_away),
                textStyle = Theme.textStyle.display.mediumMedium20,
                color = Theme.color.surfaces.onSurface
            )
            Spacer(Modifier.height(12.dp))
            MovioText(
                text = stringResource(R.string.onboarding_sub_description),
                textStyle = Theme.textStyle.label.smallRegular12,
                color = Theme.color.surfaces.onSurfaceContainer
            )

        }

        Spacer(Modifier.weight(1f))
        OnBoardingPager(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

private @Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOnBoardingScreen() {
    MovioTheme {
        OnBoardingScreen()
    }
}