package com.madrid.image_viewer.loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.Immutable
import coil.ImageLoader
import coil.decode.DataSource
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Immutable
internal class ImageLoader(private val context: Context) {
    private val TAG = "ImageLoader"

    // Create a shared loader instance with memory efficiency options
    private val loader by lazy {
        ImageLoader.Builder(context)
            .crossfade(true)
            // Using more compatible memory settings that work across Coil versions
            .allowRgb565(true) // Use RGB565 format when possible (half the memory of ARGB_8888)
            .build()
    }

    suspend fun loadBitmap(url: String, maxWidth: Int = 0, maxHeight: Int = 0): Bitmap? {
        try {
            return suspendCancellableCoroutine { continuation ->
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .allowHardware(false) // Required to access bitmap pixels
                    .apply {
                        // Apply size constraints if provided
                        if (maxWidth > 0 && maxHeight > 0) {
                            size(maxWidth, maxHeight)
                            scale(Scale.FIT)
                        }
                    }
                    .listener(
                        onStart = {
                            Log.d(TAG, "Started loading $url")
                        },
                        onCancel = {
                            if (continuation.isActive) {
                                Log.d(TAG, "Loading cancelled for $url")
                                continuation.resume(null)
                            }
                        },
                        onError = { _, throwable ->
                            if (continuation.isActive) {
                                Log.e(
                                    TAG,
                                    "Error loading $url: ${throwable.throwable.localizedMessage}"
                                )
                                if (throwable.throwable is IOException) {
                                    // For network errors, just return null instead of crashing
                                    continuation.resume(null)
                                } else {
                                    continuation.resumeWithException(throwable.throwable)
                                }
                            }
                        },
                        onSuccess = { _, result ->
                            if (continuation.isActive) {
                                val bitmap = (result.drawable as? BitmapDrawable)?.bitmap
                                Log.d(
                                    TAG,
                                    "Successfully loaded $url, fromCache: ${result.dataSource == DataSource.MEMORY_CACHE}"
                                )
                                continuation.resume(bitmap)
                            }
                        }
                    )
                    .build()

                val disposable = loader.enqueue(request)

                // Set up cancellation
                continuation.invokeOnCancellation {
                    disposable.dispose()
                }
            }
        } catch (e: CancellationException) {
            // Propagate cancellation
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Exception while loading $url: ${e.localizedMessage}")
            return null
        }
    }
}