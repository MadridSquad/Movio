package com.cairosquad.safe_image_viewer.safe_image_viewer

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import com.cairosquad.safe_image_viewer.R
import com.cairosquad.safe_image_viewer.classifier.NSFWDetector
import com.cairosquad.safe_image_viewer.loader.CoilImageLoader
import com.cairosquad.safe_image_viewer.modifier.imageBlur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * State holder for SafeImageViewer that manages the image loading, classification, and display states
 */
@Stable
class SafeImageViewerState(
    val model: String,
    initialBlurEnabled: Boolean = true,
    val nudeThreshold: Double = 0.16,
    val nonNudeThreshold: Double = 0.79,
    val enableLog: Boolean = false,
    val isBlurForced: Boolean = false,
    val onIsImageSafeChanged: (Boolean) -> Unit = {}
) {
    // Image states
    var bitmap by mutableStateOf<Bitmap?>(null)
        internal set
    var hasClassificationCompleted by mutableStateOf(false)
        internal set
    var isImageSafe by mutableStateOf(true)
        internal set
    var isBlurEnabled by mutableStateOf(initialBlurEnabled)
        internal set

    // Processing states
    var isLoading by mutableStateOf(true)
        internal set
    var loadError by mutableStateOf<String?>(null)
        internal set

    /**
     * Toggle blur visibility
     */
    fun toggleBlur() {
        isBlurEnabled = !isBlurEnabled
    }

    /**
     * Retry loading if there was an error
     */
    fun retry() {
        isLoading = true
        loadError = null
        bitmap = null
        hasClassificationCompleted = false
    }
}

/**
 * Creates and remembers a SafeImageViewerState
 */
@Composable
fun rememberSafeImageViewerState(
    model: String,
    initialBlurEnabled: Boolean = true,
    nudeThreshold: Double = 0.16,
    nonNudeThreshold: Double = 0.79,
    enableLog: Boolean = false,
    isBlurForced: Boolean = false,
    onIsImageSafeChanged: (Boolean) -> Unit = {}
): SafeImageViewerState {
    return remember(model) {
        SafeImageViewerState(
            model = model,
            initialBlurEnabled = initialBlurEnabled,
            nudeThreshold = nudeThreshold,
            nonNudeThreshold = nonNudeThreshold,
            enableLog = enableLog,
            isBlurForced = isBlurForced,
            onIsImageSafeChanged = onIsImageSafeChanged
        )
    }
}

/**
 * Displays an image with automatic NSFW classification and optional blur effect.
 *
 * This composable loads an image from the given [model], classifies it using an on-device
 * TensorFlow Lite model, and applies a blur if the content is considered inappropriate.
 *
 * - If the image is classified as NSFW, a blur will be applied (on Android S+ using `Modifier.blur`,
 *   or via a custom bitmap blur for older versions).
 * - Tapping the image toggles blur visibility if [onToggleBlur] is provided.
 *
 * @param model The image source, typically a URL or file path.
 * @param contentDescription Content description for accessibility.
 * @param modifier Modifier applied to the image container.
 * @param contentScale Scaling strategy for the image content.
 * @param alignment Alignment of the image content.
 * @param alpha Opacity level of the image (1f = fully opaque).
 * @param filterQuality Quality level for image rendering.
 * @param colorFilter Optional color filter to apply.
 * @param blur Blur radius in dp (applied only if image is classified as unsafe).
 * @param nudeThreshold Threshold for NSFW classification (lower = more sensitive) and the range is between 0 - 1.
 * @param nonNudeThreshold Threshold for SFW classification (higher = stricter) and the range is between 0 - 1.
 * @param enableLog Whether to log classification results (for debugging).
 * @param isBlurForced Force blurring regardless of classification.
 * @param placeholder Static painter shown while loading.
 * @param error Static painter shown if image loading fails.
 * @param maxWidth Maximum width for the loaded image (0 = no limit).
 * @param maxHeight Maximum height for the loaded image (0 = no limit).
 * @param animateBlur Whether to animate blur transitions.
 * @param useSystemBlur Whether to use system blur on supported devices.
 * @param onRetry Called when retry is requested after an error.
 * @param onIsImageSafeChanged Callback when the safety status changes.
 * @param loadingPlaceholder Composable shown while classification is pending.
 * @param errorContent Composable shown when image loading fails.
 * @param onToggleBlur Optional composable (e.g., icon or button) shown over blurred images.
 *                     Triggered to toggle blur state when tapped.
 */

@Composable
fun SafeImageViewer(
    // required core
    model: String,
    contentDescription: String,
    // appearance and layout
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    filterQuality: FilterQuality = DefaultFilterQuality,
    colorFilter: ColorFilter? = null,
    // NSFW and blur behavior
    blur: Int = 25,
    nudeThreshold: Double = 0.16,
    nonNudeThreshold: Double = 0.79,
    enableLog: Boolean = true,
    isBlurForced: Boolean = false,
    // Performance options
    maxWidth: Int = 0,
    maxHeight: Int = 0,
    animateBlur: Boolean = true,
    useSystemBlur: Boolean = true,
    // UI-related
    placeholder: Painter = painterResource(R.drawable.placeholder),
    error: Painter = painterResource(R.drawable.error),
    onRetry: (() -> Unit)? = null,
    onIsImageSafeChanged: (Boolean) -> Unit = {},
    loadingPlaceholder: @Composable () -> Unit = {},
    errorContent: @Composable ((String) -> Unit)? = null,
    onToggleBlur: (@Composable () -> Unit)? = null,
) {
    // Create state holder for managing component state
    val state = rememberSafeImageViewerState(
        model = model,
        initialBlurEnabled = true,
        nudeThreshold = nudeThreshold,
        nonNudeThreshold = nonNudeThreshold,
        enableLog = enableLog,
        isBlurForced = isBlurForced,
        onIsImageSafeChanged = onIsImageSafeChanged
    )

    // Local context and lifecycle for cleanup
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Keep updated references to callbacks
    val currentRetry by rememberUpdatedState(onRetry)

    // Handle lifecycle events for cleanup
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                // Cleanup resources when the component is destroyed
                NSFWDetector.shutdown()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Reset state when model changes
    LaunchedEffect(model) {
        state.bitmap = null
        state.isLoading = true
        state.loadError = null
        state.hasClassificationCompleted = false
        state.isBlurEnabled = true
        state.isImageSafe = true
    }

    // Handle image loading and classification
    LaunchedEffect(model) {
        try {
            state.isLoading = true
            state.loadError = null

            // Load bitmap with size constraints for efficiency
            withContext(Dispatchers.IO) {
                state.bitmap = CoilImageLoader(context).loadBitmap(
                    url = model,
                    maxWidth = maxWidth,
                    maxHeight = maxHeight
                )
            }

            if (state.bitmap == null) {
                state.loadError = "Failed to load image"
                state.isLoading = false
                return@LaunchedEffect
            }

            // Skip NSFW detection if thresholds are disabled
            if (nudeThreshold == 0.0 && nonNudeThreshold == 0.0) {
                state.isImageSafe = true
                state.hasClassificationCompleted = true
                state.isLoading = false
                return@LaunchedEffect
            }

            // Perform NSFW detection
            withContext(Dispatchers.Default) {
                NSFWDetector.isNSFWCancellable(
                    bitmap = state.bitmap!!,
                    enableLog = enableLog,
                    nudeThreshold = nudeThreshold,
                    nonNudeThreshold = nonNudeThreshold,
                    imageUrl = model,
                    isActive = { true },
                    callback = { isNSFW ->
                        state.isImageSafe = !isNSFW
                        state.hasClassificationCompleted = true
                        state.isLoading = false
                        onIsImageSafeChanged(state.isImageSafe)
                    }
                )
            }
        } catch (e: Exception) {
            if (enableLog) {
                Log.e("SafeImageViewer", "Error processing image: ${e.localizedMessage}")
            }
            state.loadError = e.localizedMessage ?: "Unknown error"
            state.isLoading = false
        }
    }

    // Handle propagating state changes
    LaunchedEffect(state.isImageSafe) {
        onIsImageSafeChanged(state.isImageSafe)
    }

    // Main UI rendering
    Crossfade(
        modifier = modifier,
        targetState = when {
            state.isLoading -> "loading"
            state.loadError != null -> "error"
            state.hasClassificationCompleted && state.bitmap != null -> "success"
            else -> "loading"
        },
        animationSpec = tween(durationMillis = 300)
    ) { currentState ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (currentState) {
                "loading" -> {
                    loadingPlaceholder()
                }

                "error" -> {
                    if (errorContent != null && state.loadError != null) {
                        errorContent(state.loadError!!)
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = null,
                                contentDescription = "Error",
                                placeholder = error,
                                error = error,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = contentScale,
                            )

                            if (onRetry != null) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .clickable { currentRetry?.invoke() }
                                ) {
                                    // Retry UI
                                }
                            }
                        }
                    }
                }

                "success" -> {
                    if (state.bitmap != null) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .imageBlur(
                                    isBlurEnabled = state.isBlurEnabled,
                                    isImageSafe = state.isImageSafe,
                                    blur = blur.dp,
                                    isBlurForced = isBlurForced,
                                    bitmap = state.bitmap!!,
                                    animate = animateBlur,
                                    useSystemBlur = useSystemBlur
                                ),
                            model = state.bitmap,
                            contentDescription = contentDescription,
                            contentScale = contentScale,
                            filterQuality = filterQuality,
                            alpha = alpha,
                            alignment = alignment,
                            colorFilter = colorFilter,
                            placeholder = placeholder,
                            error = error,
                        )

                        // Show toggle blur UI if image is not safe
                        AnimatedVisibility(
                            visible = !state.isImageSafe && onToggleBlur != null,
                            enter = fadeIn(tween(300)),
                            exit = fadeOut(tween(300)),
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable { state.toggleBlur() }
                            ) {
                                onToggleBlur?.invoke()
                            }
                        }
                    }
                }
            }
        }
    }
}
