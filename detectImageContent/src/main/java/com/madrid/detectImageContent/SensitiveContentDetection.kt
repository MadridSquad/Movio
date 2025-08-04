package com.madrid.detectImageContent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject

class SensitiveContentDetection @Inject constructor(
    private val context: Context
) {

    fun sensitiveContentDetection(bitmap: Bitmap): Boolean {
        val model = Interpreter(loadModelFile(context))
        val output = Array(1) { FloatArray(LABELS.size) }
        model.run(prepareInputBitmap(bitmap), output)
        val result = LABELS.zip(output[0].toList()).toMap()
        val pornScore = result["porn"] ?: 0f
        val sexyScore = result["sexy"] ?: 0f
        val hentaiScore = result["hentai"] ?: 0f
        return pornScore > SENSITIVE_THRESHOLD || sexyScore > SENSITIVE_THRESHOLD
                || hentaiScore > SENSITIVE_THRESHOLD
    }

    private fun prepareInputBitmap(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
        val input =
            Array(1) {
                Array(MODEL_INPUT_SIZE) {
                    Array(MODEL_INPUT_SIZE) { FloatArray(3) }
                }
            }
        val resized = bitmap.scale(MODEL_INPUT_SIZE, MODEL_INPUT_SIZE)

        for (y in 0 until MODEL_INPUT_SIZE) {
            for (x in 0 until MODEL_INPUT_SIZE) {
                val pixel = resized[x, y]
                input[0][y][x][0] = Color.red(pixel) / 255.0f
                input[0][y][x][1] = Color.green(pixel) / 255.0f
                input[0][y][x][2] = Color.blue(pixel) / 255.0f
            }
        }
        return input
    }

    private fun loadModelFile(context: Context): ByteBuffer {
        val fileDescriptor = context.assets.openFd(MODEL_FILE_NAME)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    companion object {
        private const val MODEL_FILE_NAME = "nsfw_detect.tflite"
        private const val MODEL_INPUT_SIZE = 224
        private const val SENSITIVE_THRESHOLD = 0.35f
        private val LABELS = listOf("drawings", "hentai", "neutral", "porn", "sexy")
    }

}
