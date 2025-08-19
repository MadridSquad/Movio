package com.madrid.presentation.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.floor


fun formatRate(rate: Double): String {
    return try {
        val numericRate = rate
        val truncated = floor(numericRate * 10) / 10
        if (truncated == truncated.toInt().toDouble()) {
            truncated.toInt().toString()
        } else {
            DecimalFormat("#.#").format(truncated)
        }
    } catch (e: Exception) {
        rate.toString()
    }
}

fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return ""

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: ""
    } catch (e: Exception) {
        try {
            val fallbackFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = fallbackFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            dateString ?: ""
        }
    }
}