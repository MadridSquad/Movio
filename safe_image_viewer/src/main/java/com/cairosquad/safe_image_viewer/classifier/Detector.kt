package com.cairosquad.safe_image_viewer.classifier

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.graphics.scale
import com.cairosquad.safe_image_viewer.cache.DiskCacheManager
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Detector {
    private const val TAG = "Detector"
    private const val LABEL_SFW = "nude"
    private const val LABEL_NSFW = "nonnude"
    private const val NUMBER_OF_CACHES_IMAGES = 200 // Increased cache size
    private const val CACHE_EXPIRY_MINUTES = 60L // Longer expiry time
    private const val THUMBNAIL_SIZE = 32

    // Use available processors to determine thread pool size with sensible limits
    private val THREAD_POOL_SIZE = (Runtime.getRuntime().availableProcessors() - 1).coerceIn(1, 8)

    // Coroutine scope for disk operations with proper lifecycle management
    private var ioScopeJob: Job? = null
    private var ioScope: CoroutineScope? = null

    // Your DiskCacheManager integration
    private var diskCacheManager: DiskCacheManager? = null

    // Size-aware cache with weak references to allow GC when needed
    private data class CacheEntry(
        val result: Boolean,
        val timestamp: Long = System.currentTimeMillis()
    )

    // In-flight detection tracking to prevent duplicate processing
    private data class PendingDetection(
        val callbacks: MutableList<(Boolean) -> Unit> = mutableListOf()
    )

    // Track in-progress detections to avoid duplicate work
    private val pendingDetections = ConcurrentHashMap<String, PendingDetection>()

    // Enhanced cache with better expiry strategy
    private val imageCache =
        object : LinkedHashMap<String, CacheEntry>(NUMBER_OF_CACHES_IMAGES, 0.75f, true) {
            override fun removeEldestEntry(eldest: Map.Entry<String, CacheEntry>): Boolean {
                // Limit by size and also by age
                val tooOld =
                    (System.currentTimeMillis() - eldest.value.timestamp) > TimeUnit.MINUTES.toMillis(
                        CACHE_EXPIRY_MINUTES
                    )
                return size > NUMBER_OF_CACHES_IMAGES || tooOld
            }
        }

    // Persistent cache for bitmap hashes to reduce processing of identical image content
    private val hashCache = ConcurrentHashMap<String, String>(100)

    // Lazy initialization - only create when first used
    private val localModel by lazy {
        FirebaseAutoMLLocalModel.Builder()
            .setAssetFilePath("manifest.json")
            .build()
    }

    private val options by lazy {
        FirebaseVisionOnDeviceAutoMLImageLabelerOptions
            .Builder(localModel)
            .setConfidenceThreshold(0.1f)
            .build()
    }

    private val interpreter by lazy {
        FirebaseVision.getInstance()
            .getOnDeviceAutoMLImageLabeler(options)
    }

    private val executorService: ExecutorService by lazy {
        Executors.newFixedThreadPool(THREAD_POOL_SIZE)
    }

    /**
     * Initialize the detector with your DiskCacheManager
     */
    fun initialize(context: Context) {
        // Initialize your DiskCacheManager
        diskCacheManager = DiskCacheManager.getInstance(context.applicationContext)

        // Setup IO scope for disk operations
        ioScopeJob = SupervisorJob()
        ioScope = CoroutineScope(Dispatchers.IO + ioScopeJob!!)

        Log.d(TAG, "Detector initialized with DiskCacheManager")
    }

    /**
     * Clears all cached results to free memory
     */
    fun clearCache() {
        synchronized(imageCache) {
            imageCache.clear()
        }
        hashCache.clear()

        // Clear your disk cache
        diskCacheManager?.let { cache ->
            ioScope?.launch {
                cache.clearAll()
            }
        }
    }

    /**
     * Shutdown and cleanup resources
     */
    fun shutdown() {
        // Cancel coroutine scope
        ioScopeJob?.cancel()
        ioScope = null
        ioScopeJob = null

        // Shutdown thread pool
        executorService.shutdown()
        try {
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow()
            }
        } catch (e: InterruptedException) {
            executorService.shutdownNow()
            Thread.currentThread().interrupt()
        }

        // Close your disk cache
        diskCacheManager?.close()
        diskCacheManager = null
        clearCache()
    }

    /**
     * Computes a hash for the bitmap content to identify identical images
     * Downsamples for speed - we just need a rough fingerprint, not exact matching
     */
    private fun computeBitmapHash(bitmap: Bitmap): String {
        // Create a tiny thumbnail to use for quick comparison
        val thumbSize = 32
        val thumb = Bitmap.createScaledBitmap(bitmap, thumbSize, thumbSize, true)

        // Sample pixels in a grid pattern (every 4th pixel) for speed
        val pixelData = IntArray(64)
        var idx = 0
        for (y in 0 until thumbSize step 4) {
            for (x in 0 until thumbSize step 4) {
                if (idx < pixelData.size) {
                    pixelData[idx++] = thumb.getPixel(x, y)
                }
            }
        }

        // Use the sampled pixels to create a hash
        return pixelData.fold(0) { acc, pixel ->
            31 * acc + pixel
        }.toString()
    }

    /**
     * Creates a robust cache key based on image content, URL and parameters
     */
    private fun createCacheKey(
        bitmap: Bitmap,
        imageUrl: String,
        nudeThreshold: Double,
        nonNudeThreshold: Double
    ): String {
        // Check if we've seen this exact bitmap before
        val bitmapHash = hashCache.getOrPut(imageUrl) {
            computeBitmapHash(bitmap)
        }

        // Create a cache key that combines the image fingerprint and parameters
        return "$bitmapHash-$nudeThreshold-$nonNudeThreshold"
    }

    fun isNSFWCancellable(
        bitmap: Bitmap,
        nudeThreshold: Double,
        nonNudeThreshold: Double,
        imageUrl: String,
        enableLog: Boolean = false,
        isActive: () -> Boolean,
        callback: (isNSFW: Boolean) -> Unit
    ) {
        // Early exit if not active
        if (!isActive()) {
            if (enableLog) Log.d(TAG, "Processing cancelled - not active")
            return
        }

        // Validate thresholds
        val validatedNudeThreshold = nudeThreshold.coerceIn(0.0, 1.0)
        val validatedNonNudeThreshold = nonNudeThreshold.coerceIn(0.0, 1.0)

        // Generate cache key based on bitmap content and thresholds
        val cacheKey =
            createCacheKey(bitmap, imageUrl, validatedNudeThreshold, validatedNonNudeThreshold)

        // STEP 1: Check memory cache first for instant hit
        synchronized(imageCache) {
            imageCache[cacheKey]?.let { cachedResult ->
                if (isActive()) {
                    if (enableLog) Log.d(TAG, "Memory cache hit for image: $imageUrl")
                    callback(cachedResult.result)
                }
                return
            }
        }

        // STEP 2: Check your DiskCacheManager for persistent cache
        ioScope?.launch {
            try {
                val diskResult = diskCacheManager?.getResult(cacheKey)
                if (diskResult != null && isActive()) {
                    if (enableLog) Log.d(TAG, "Disk cache hit for image: $imageUrl")

                    // Update memory cache with disk result
                    synchronized(imageCache) {
                        imageCache[cacheKey] = CacheEntry(diskResult)
                    }

                    // Return result from disk cache on main thread
                    Handler(Looper.getMainLooper()).post {
                        if (isActive()) {
                            callback(diskResult)
                        }
                    }
                    return@launch
                }

                // STEP 3: Save thumbnail to disk for future fingerprinting
                if (diskCacheManager?.hasThumbnail(imageUrl) == false) {
                    val thumb =
                        Bitmap.createScaledBitmap(bitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, true)
                    try {
                        diskCacheManager?.saveThumbnail(imageUrl, thumb)
                    } finally {
                        thumb.recycle() // Clean up thumbnail
                    }
                }

                // Continue with detection process on main thread
                Handler(Looper.getMainLooper()).post {
                    processDetectionWithConcurrency(
                        bitmap, cacheKey, imageUrl,
                        validatedNudeThreshold, validatedNonNudeThreshold,
                        enableLog, isActive, callback
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error checking disk cache: ${e.message}")

                // Fall back to regular processing
                Handler(Looper.getMainLooper()).post {
                    processDetectionWithConcurrency(
                        bitmap, cacheKey, imageUrl,
                        validatedNudeThreshold, validatedNonNudeThreshold,
                        enableLog, isActive, callback
                    )
                }
            }
        }
    }

    private fun processDetectionWithConcurrency(
        bitmap: Bitmap,
        cacheKey: String,
        imageUrl: String,
        nudeThreshold: Double,
        nonNudeThreshold: Double,
        enableLog: Boolean,
        isActive: () -> Boolean,
        callback: (isNSFW: Boolean) -> Unit
    ) {
        // If detection is already in progress for this image, join that request
        synchronized(pendingDetections) {
            val existingDetection = pendingDetections[cacheKey]
            if (existingDetection != null) {
                if (enableLog) Log.d(TAG, "Detection already in progress for: $imageUrl")
                existingDetection.callbacks.add { result ->
                    if (isActive()) {
                        callback(result)
                    }
                }
                return
            }

            // Start a new detection - register as pending first
            pendingDetections[cacheKey] = PendingDetection(mutableListOf(callback))
        }

        // Check if still active before expensive operations
        if (!isActive()) {
            removePendingDetection(cacheKey)
            return
        }

        // Hold bitmap with weak reference
        val weakBitmap = WeakReference(bitmap)

        // Optimize bitmap for processing
        val optimizedBitmap = optimizeBitmap(bitmap)

        // Check again before Firebase call
        if (!isActive()) {
            removePendingDetection(cacheKey)
            return
        }

        val image = FirebaseVisionImage.fromBitmap(optimizedBitmap)

        interpreter.processImage(image)
            .addOnSuccessListener { labels ->
                if (!isActive()) {
                    if (enableLog) Log.d(TAG, "Processing cancelled after Firebase success")
                    removePendingDetection(cacheKey)
                    return@addOnSuccessListener
                }

                executorService.execute {
                    try {
                        if (!isActive()) {
                            if (enableLog) Log.d(
                                TAG,
                                "Processing cancelled during label processing"
                            )
                            removePendingDetection(cacheKey)
                            return@execute
                        }

                        val result =
                            processLabels(labels, nudeThreshold, nonNudeThreshold, enableLog)

                        // Store in memory cache
                        synchronized(imageCache) {
                            imageCache[cacheKey] = CacheEntry(result)
                        }

                        // IMPORTANT: Save result to your DiskCacheManager
                        ioScope?.launch {
                            diskCacheManager?.saveResult(cacheKey, result)
                        }

                        // Get all pending callbacks
                        val callbacks = synchronized(pendingDetections) {
                            val pending = pendingDetections[cacheKey]?.callbacks ?: listOf()
                            pendingDetections.remove(cacheKey)
                            pending
                        }

                        // Return result on main thread to all waiting callbacks
                        Handler(Looper.getMainLooper()).post {
                            for (cb in callbacks) {
                                cb(result)
                            }
                        }

                    } catch (e: Exception) {
                        if (enableLog) Log.e(TAG, "Error processing labels: ${e.localizedMessage}")

                        val callbacks = synchronized(pendingDetections) {
                            val pending = pendingDetections[cacheKey]?.callbacks ?: listOf()
                            pendingDetections.remove(cacheKey)
                            pending
                        }

                        Handler(Looper.getMainLooper()).post {
                            for (cb in callbacks) {
                                cb(false) // Default to false (safe) on error
                            }
                        }
                    } finally {
                        weakBitmap.clear()
                    }
                }
            }
            .addOnFailureListener { e ->
                if (enableLog) Log.e(TAG, "NSFW detection failed: ${e.localizedMessage}")

                val callbacks = synchronized(pendingDetections) {
                    val pending = pendingDetections[cacheKey]?.callbacks ?: listOf()
                    pendingDetections.remove(cacheKey)
                    pending
                }

                Handler(Looper.getMainLooper()).post {
                    for (cb in callbacks) {
                        cb(false)
                    }
                }
            }
    }

    /**
     * Clean up a pending detection if it's cancelled
     */
    private fun removePendingDetection(cacheKey: String) {
        synchronized(pendingDetections) {
            pendingDetections.remove(cacheKey)
        }
    }

    private fun processLabels(
        labels: List<FirebaseVisionImageLabel>,
        nudeThreshold: Double,
        nonNudeThreshold: Double,
        enableLog: Boolean
    ): Boolean {
        if (labels.isEmpty()) {
            if (enableLog) Log.d(TAG, "No labels detected")
            return false
        }

        // Process all relevant labels, not just the first one
        var maxNudeConfidence = 0f
        var maxNonNudeConfidence = 0f

        for (label in labels) {
            if (enableLog) {
                Log.d(TAG, "Label: ${label.text}, Confidence: ${label.confidence}")
            }

            when (label.text) {
                LABEL_SFW -> {
                    maxNudeConfidence = maxOf(maxNudeConfidence, label.confidence)
                }

                LABEL_NSFW -> {
                    maxNonNudeConfidence = maxOf(maxNonNudeConfidence, label.confidence)
                }
            }
        }

        // Improved logic: compare both confidences
        return when {
            maxNudeConfidence > nudeThreshold -> true
            maxNonNudeConfidence > nonNudeThreshold -> false
            maxNudeConfidence > maxNonNudeConfidence -> true
            else -> false
        }
    }

    private fun optimizeBitmap(bitmap: Bitmap): Bitmap {
        // Skip if bitmap is too small to need optimization
        if (bitmap.width <= 512 && bitmap.height <= 512) return bitmap

        // Resize if too large (ML models work better with smaller, consistent sizes)
        val maxSize = 512
        val ratio = minOf(
            maxSize.toFloat() / bitmap.width,
            maxSize.toFloat() / bitmap.height
        )
        val newWidth = (bitmap.width * ratio).toInt()
        val newHeight = (bitmap.height * ratio).toInt()

        return bitmap.scale(newWidth, newHeight, filter = true)
    }
}
