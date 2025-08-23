package com.madrid.presentation.viewModel.shared.parser

import java.util.Locale


fun formatDuration(durationInMinutes: String): String {
    val minutes = durationInMinutes.toIntOrNull() ?: return "Unknown"
    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    val localeTag = Locale.getDefault().toLanguageTag()
    return when (localeTag) {
        "ar" -> {
            val hourPart = if (hours > 0) "$hours ساعة " else ""
            val minutePart = if (remainingMinutes > 0) "$remainingMinutes دقيقة" else ""
            convertEnglishDigitsToArabic((hourPart + minutePart).trim().ifEmpty { "0 دقيقة" })
        }
        else -> {
            val hourPart = if (hours > 0) "$hours h " else ""
            val minutePart = if (remainingMinutes > 0) "$remainingMinutes min" else ""
            (hourPart + minutePart).trim().ifEmpty { "0 min" }
        }
    }
}