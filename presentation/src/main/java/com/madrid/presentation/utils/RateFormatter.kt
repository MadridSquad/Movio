package com.madrid.presentation.utils

import java.text.DecimalFormat

object RateFormatter {
    fun formatRate(rate: Double): String {
        return try {
            val truncated = Math.floor(rate * 10) / 10
            if (truncated == truncated.toInt().toDouble()) {
                truncated.toInt().toString()
            } else {
                DecimalFormat("#.#").format(truncated)
            }
        } catch (e: Exception) {
            rate.toString()
        }
    }
    fun formatRate(rate: String): String {
        return try {
            val rateValue = rate.toDouble()
            formatRate(rateValue)
        } catch (e: NumberFormatException) {
            rate
        }
    }
}