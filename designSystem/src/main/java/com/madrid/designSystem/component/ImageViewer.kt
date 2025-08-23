package com.madrid.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.LocalIsDarkTheme

/**
 * A customizable image viewer component that uses SubcomposeAsyncImage for efficient image loading.
 * Passes through all the exact same parameters as SubcomposeAsyncImage.
 *
 * @param model Either an [ImageRequest] or the [ImageRequest.data] value.
 * @param contentDescription Text used by accessibility services to describe what this image represents.
 * @param modifier Modifier used to adjust the layout algorithm or draw decoration content.
 * @param loading An optional composable that is displayed while the image is loading.
 * @param success An optional composable that is displayed when the image is successfully loaded.
 * @param error An optional composable that is displayed when the image fails to load.
 * @param onLoading Called when the image request begins loading.
 * @param onSuccess Called when the image request completes successfully.
 * @param onError Called when the image request completes unsuccessfully.
 * @param alignment Optional alignment parameter used to place the [AsyncImagePainter] in the given bounds defined by the width and height.
 * @param contentScale Optional scale parameter used to determine the aspect ratio scaling to be used if the bounds are a different size from the intrinsic size of the image.
 * @param alpha Optional opacity to be applied to the image when it is rendered onscreen.
 * @param colorFilter Optional [ColorFilter] to apply for the image when it is rendered onscreen.
 * @param filterQuality Sampling algorithm applied to a bitmap when it is scaled and drawn into the destination.
 */
@Composable
fun ImageViewer(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    loading: (@Composable () -> Unit)? = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .shimmerEffect(),
        )
    },
    success: (@Composable () -> Unit)? = null,
    error: (@Composable () -> Unit)? = {
        val isDarkTheme = LocalIsDarkTheme.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkTheme) Color(0xff252E3C) else Color(0xffA1AEC3)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(0.3f),
                imageVector = ImageVector.vectorResource(id = R.drawable.placeholder_icon),
                contentDescription = stringResource(R.string.image_loading_error),
                colorFilter = ColorFilter.tint(Color(0xFFEFF1F5))
            )
        }
    },
    onLoading: (() -> Unit)? = null,
    onSuccess: (() -> Unit)? = null,
    onError: () -> Unit = {},
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        loading = loading?.let { { it() } },
        success = success?.let { { it() } },
        error = error?.let { { it() } },
        onLoading = onLoading?.let { { it() } },
        onSuccess = onSuccess?.let { { it() } },
        onError = { onError() },
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
    )
}
