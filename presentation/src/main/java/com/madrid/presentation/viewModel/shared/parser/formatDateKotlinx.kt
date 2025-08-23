package com.madrid.presentation.viewModel.shared.parser

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.util.Locale


fun formatDateKotlinx(dateString: String): String {
    try {
        val date: LocalDate = dateString.toLocalDate()
        val month = date.monthNumber.toString().padStart(2, '0')
        val day = date.dayOfMonth.toString().padStart(2, '0')
        val year = date.year.toString()

        val language = Locale.getDefault().toLanguageTag()
        return when (language) {
            "en" -> {
                "$month/$day/$year"
            }
            "ar" -> {
                convertEnglishDigitsToArabic("$year/$month/$day")
            }
            else -> {
                "$day/$month/$year"
            }
        }
    } catch (e: Exception) {
        return ""
    }
}