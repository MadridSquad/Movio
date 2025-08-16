package com.cairosquad.safe_image_viewer.modifier

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.Dp

/**
 * Advanced image blur modifier that selects the optimal blur implementation based on device capabilities
 * and user preferences.
 *
 * @param blur Blur radius in Dp
 * @param bitmap Source bitmap to blur
 * @param isBlurEnabled Whether blur should be applied
 * @param isBlurForced Force blur regardless of image safety status
 * @param isImageSafe Whether the image is considered safe (not NSFW)
 * @param animate Whether to animate the blur transition
 * @param useSystemBlur Whether to use the system blur on Android 12+ (may be more efficient but lower quality)
 */
@Stable
fun Modifier.imageBlur(
    blur: Dp,
    bitmap: Bitmap,
    isBlurEnabled: Boolean = true,
    isBlurForced: Boolean = false,
    isImageSafe: Boolean = false,
    animate: Boolean = true,
    useSystemBlur: Boolean = true
): Modifier {
    // Determine if we should apply blur
    val shouldApplyBlur = isBlurForced || (!isImageSafe && isBlurEnabled)

    if (!shouldApplyBlur) return this

    // Use system blur for Android 12+ (S) if allowed, otherwise use our custom implementation
    return if (useSystemBlur && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // System blur implementation (more efficient on newer devices)
        this.blur(blur)
    } else {
        // Custom blur implementation with progressive loading
        this.fastBlur(
            sourceBitmap = bitmap,
            radius = blur.value.toInt(),
            animate = animate
        )
    }
}