package com.cairosquad.safe_image_viewer.modifier

import android.graphics.Bitmap
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.IntSize
import com.cairosquad.safe_image_viewer.alghorithm.fastBlurBitmap
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Fast blur modifier with progressive loading for better UX
 *
 * @param sourceBitmap The bitmap to blur
 * @param radius The blur radius
 * @param animate Whether to animate the blur transition
 */
@Stable
fun Modifier.fastBlur(
    sourceBitmap: Bitmap,
    radius: Int = 6,
    animate: Boolean = true
): Modifier = composed {
    var blurredBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var quickBlurredBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // Track if high-quality blur is ready
    var isHighQualityReady by remember { mutableStateOf(false) }

    // Animation alpha for smooth transition
    val blurAlpha = remember { Animatable(0f) }

    // Generate two levels of blur - fast preview and higher quality final
    LaunchedEffect(sourceBitmap, radius) {
        // Reset states
        isHighQualityReady = false

        coroutineScope {
            // Quick blur for immediate feedback (very low radius)
            val quickBlurJob = async {
                val quickRadius = (radius * 0.3f).toInt().coerceAtLeast(1)
                fastBlurBitmap(sourceBitmap, quickRadius).asImageBitmap()
            }

            // High quality blur (takes longer)
            val highQualityBlurJob = async {
                fastBlurBitmap(sourceBitmap, radius).asImageBitmap()
            }

            // Set quick blur as soon as it's ready
            quickBlurredBitmap = quickBlurJob.await()

            // Then set high quality when ready
            blurredBitmap = highQualityBlurJob.await()
            isHighQualityReady = true

            // Animate the transition if requested
            if (animate) {
                launch {
                    blurAlpha.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 300)
                    )
                }
            } else {
                blurAlpha.snapTo(1f)
            }
        }
    }

    if (blurredBitmap != null || quickBlurredBitmap != null) {
        this.then(
            Modifier.drawWithContent {
                if (isHighQualityReady && blurredBitmap != null) {
                    // Draw high-quality blur when ready
                    drawImage(
                        image = blurredBitmap!!,
                        dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                        alpha = if (animate) blurAlpha.value else 1f
                    )
                } else if (quickBlurredBitmap != null) {
                    // Draw quick blur as preview
                    drawImage(
                        image = quickBlurredBitmap!!,
                        dstSize = IntSize(size.width.toInt(), size.height.toInt())
                    )
                }
            }
        )
    } else {
        // If no blur is ready yet, just return the original modifier
        this
    }
}
