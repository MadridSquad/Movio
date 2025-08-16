package com.cairosquad.safe_image_viewer.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.MessageDigest

/**
 * Manages disk-based caching of NSFW detection results and image thumbnails
 * using Android's standard cache directory
 */
class DiskCacheManager(context: Context) {
    private val TAG = "DiskCacheManager"

    // Cache directories
    private val resultsCacheDir: File
    private val thumbsCacheDir: File

    init {
        // Create cache directories inside the app's cache folder
        val cacheDir = context.cacheDir
        resultsCacheDir = File(cacheDir, "nsfw_results")
        thumbsCacheDir = File(cacheDir, "nsfw_thumbs")

        // Ensure directories exist
        resultsCacheDir.mkdirs()
        thumbsCacheDir.mkdirs()

        Log.d(
            TAG,
            "Disk cache initialized at ${resultsCacheDir.absolutePath} and ${thumbsCacheDir.absolutePath}"
        )
    }

    /**
     * Saves a detection result to disk
     */
    suspend fun saveResult(key: String, isNSFW: Boolean): Boolean = withContext(Dispatchers.IO) {
        try {
            val hashedKey = hashKey(key)
            val file = File(resultsCacheDir, hashedKey)

            FileOutputStream(file).use { outputStream ->
                outputStream.write(if (isNSFW) 1 else 0)
                Log.d(TAG, "Saved result for key: $key")
                return@withContext true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving result: ${e.message}")
            return@withContext false
        }
    }

    /**
     * Loads a detection result from disk
     */
    suspend fun getResult(key: String): Boolean? = withContext(Dispatchers.IO) {
        try {
            val hashedKey = hashKey(key)
            val file = File(resultsCacheDir, hashedKey)

            if (!file.exists()) {
                return@withContext null
            }

            FileInputStream(file).use { inputStream ->
                val value = inputStream.read()
                Log.d(TAG, "Loaded result for key: $key")
                return@withContext value == 1
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading result: ${e.message}")
            return@withContext null
        }
    }

    /**
     * Saves a bitmap thumbnail to disk
     */
    suspend fun saveThumbnail(key: String, bitmap: Bitmap): Boolean = withContext(Dispatchers.IO) {
        try {
            val hashedKey = hashKey(key)
            val file = File(thumbsCacheDir, hashedKey)

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.WEBP, 90, outputStream)
                Log.d(TAG, "Saved thumbnail for key: $key")
                return@withContext true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving thumbnail: ${e.message}")
            return@withContext false
        }
    }

    /**
     * Loads a bitmap thumbnail from disk
     */
    suspend fun getThumbnail(key: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val hashedKey = hashKey(key)
            val file = File(thumbsCacheDir, hashedKey)

            if (!file.exists()) {
                return@withContext null
            }

            FileInputStream(file).use { inputStream ->
                return@withContext BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading thumbnail: ${e.message}")
            return@withContext null
        }
    }

    /**
     * Checks if a result exists in the cache
     */
    suspend fun hasResult(key: String): Boolean = withContext(Dispatchers.IO) {
        val hashedKey = hashKey(key)
        val file = File(resultsCacheDir, hashedKey)
        return@withContext file.exists()
    }

    /**
     * Checks if a thumbnail exists in the cache
     */
    suspend fun hasThumbnail(key: String): Boolean = withContext(Dispatchers.IO) {
        val hashedKey = hashKey(key)
        val file = File(thumbsCacheDir, hashedKey)
        return@withContext file.exists()
    }

    /**
     * Clears all cached data
     */
    suspend fun clearAll() = withContext(Dispatchers.IO) {
        try {
            // Delete all files in result directory
            resultsCacheDir.listFiles()?.forEach { it.delete() }

            // Delete all files in thumbnail directory
            thumbsCacheDir.listFiles()?.forEach { it.delete() }

            Log.d(TAG, "Cleared all disk caches")
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing cache: ${e.message}")
        }
    }

    /**
     * Creates a safe filename hash from a cache key
     */
    private fun hashKey(key: String): String {
        val md = MessageDigest.getInstance("MD5")
        val hash = md.digest(key.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    /**
     * Close resources - not needed with standard cache directory
     */
    fun close() {
        // Nothing to close with standard file system
    }

    companion object {
        @Volatile
        private var INSTANCE: DiskCacheManager? = null

        fun getInstance(context: Context): DiskCacheManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DiskCacheManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
