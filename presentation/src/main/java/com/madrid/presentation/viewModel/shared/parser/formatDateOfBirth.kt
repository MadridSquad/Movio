package com.madrid.presentation.viewModel.shared.parser

import java.util.Locale

fun formatDateOfBirth(rawDate: String): String {
    val monthsMap = mapOf(
        "en" to listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        ),
        "ar" to listOf(
            "يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو",
            "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"
        )
    )

    val localeTag = Locale.getDefault().toLanguageTag()

    return try {
        val parts = rawDate.split("-")
        if (parts.size != 3) return "Unknown"

        val year = parts[0]
        val monthIndex = parts[1].toIntOrNull()?.minus(1) ?: return "Unknown"
        val day = parts[2].toIntOrNull() ?: return "Unknown"

        val months = monthsMap[localeTag.substring(0, 2)] ?: monthsMap["en"]!!
        val monthName = months.getOrNull(monthIndex) ?: return "Unknown"
        return when (localeTag) {
            "ar" -> convertEnglishDigitsToArabic("$day $monthName $year")
            else -> "$monthName $day, $year"
        }
    } catch (e: Exception) {
        "Unknown"
    }
}

