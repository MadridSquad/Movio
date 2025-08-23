package com.madrid.presentation.viewModel.shared.parser

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.floor


fun formatRate(rate: Double): String {
    try {
        val numericRate = rate
        val truncated = floor(numericRate * 10) / 10
        val result = if (truncated == truncated.toInt().toDouble()) {
            truncated.toInt().toString()
        } else {
            DecimalFormat("#.#").format(truncated)
        }
        return when (Locale.getDefault().toLanguageTag()) {
            "ar" -> convertEnglishDigitsToArabic(result)
            else -> result
        }
    } catch (e: Exception) {
        return rate.toString()
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