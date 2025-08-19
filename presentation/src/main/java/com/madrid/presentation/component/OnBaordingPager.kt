package com.madrid.presentation.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import kotlinx.coroutines.delay

@Composable
fun OnBoardingPager(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var swipeProgress by remember { mutableFloatStateOf(0f) }
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl


    val navController = LocalNavController.current

    LaunchedEffect(swipeProgress) {
        if (swipeProgress == 1f) {
            delay(200)
            context.vibrateDevice()
            navController.navigate(Destinations.LoginScreen) {
                popUpTo(Destinations.OnBoarding) {
                    inclusive = true
                }
            }
        }
    }


    val animatedProgress by animateFloatAsState(
        targetValue = swipeProgress,
    )


    val popCornBoxWidth = lerp(
        start = 48.dp,
        stop = 328.dp,
        fraction = animatedProgress
    )

    val popCornIconOffset = lerp(
        start = 12.dp,
        stop = 117.dp,
        fraction = animatedProgress
    )

    val textOffset = lerp(
        start = 132.dp,
        stop = 145.dp,
        fraction = animatedProgress
    )

    val textColor by animateColorAsState(
        if (swipeProgress >= 0.5) Theme.color.brand.onPrimary else Theme.color.surfaces.onSurface
    )


    Box(
        modifier = modifier
            .height(48.dp)
            .width(328.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Theme.color.surfaces.surfaceContainer),
    ) {

        Box(
            Modifier
                .height(48.dp)
                .width(popCornBoxWidth)
                .clip(RoundedCornerShape(24.dp))
                .background(Theme.color.brand.primary)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val sensitivity = 0.002f
                        val direction = if (isRtl) -1 else 1
                        swipeProgress =
                            (swipeProgress + direction * delta * sensitivity).coerceIn(0f, 1f)
                    },
                    onDragStopped = { velocity ->
                        val forward = if (isRtl) velocity < -1000f else velocity > 1000f
                        val backward = if (isRtl) velocity > 1000f else velocity < -1000f

                        swipeProgress = when {
                            forward -> 1f
                            backward -> 0f
                            swipeProgress >= 0.5f -> 1f
                            else -> 0f
                        }
                    }
                )

        ) {
            Image(
                painter = painterResource(R.drawable.pop_corn),
                contentDescription = "onboarding",
                modifier = Modifier
                    .padding(start = popCornIconOffset, top = 12.dp)
                    .graphicsLayer {
                        rotationY = if (isRtl) 180f else 0f
                    }
            )
        }


        MovioText(
            text = stringResource(R.string.start_now),
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = textColor,
            modifier = Modifier.padding(top = 15.5.dp, start = textOffset),
        )
        Image(
            painter = painterResource(R.drawable.onboarding_single_arrow),
            contentDescription = "onboarding",
            modifier = Modifier
                .padding(top = 16.5.dp, start = 280.25.dp)
                .graphicsLayer {
                    rotationY = if (isRtl) 180f else 0f
                },
            colorFilter = ColorFilter.tint(Theme.color.surfaces.onSurfaceAt3)
        )
        Image(
            painter = painterResource(R.drawable.onboarding_single_arrow),
            contentDescription = "onboarding",
            modifier = Modifier
                .padding(top = 16.5.dp, start = 292.25.dp)
                .graphicsLayer {
                    rotationY = if (isRtl) 180f else 0f
                },
            colorFilter = ColorFilter.tint(Theme.color.surfaces.onSurfaceAt2)
        )
        Image(
            painter = painterResource(R.drawable.onboarding_single_arrow),
            contentDescription = "onboarding",
            modifier = Modifier
                .padding(top = 16.5.dp, start = 304.25.dp)
                .graphicsLayer {
                    rotationY = if (isRtl) 180f else 0f
                },
            colorFilter = ColorFilter.tint(Theme.color.surfaces.onSurfaceAt1)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPager() {
    MovioTheme {
        OnBoardingPager(modifier = Modifier.fillMaxWidth())
    }
}

@SuppressLint("MissingPermission")
fun Context.vibrateDevice(durationMillis: Long = 50) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = getSystemService(VibratorManager::class.java)
        val vibrator = vibratorManager.defaultVibrator
        vibrator.vibrate(
            VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    } else {
        @Suppress("DEPRECATION")
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        @Suppress("DEPRECATION")
        vibrator.vibrate(durationMillis)
    }
}