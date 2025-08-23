package com.madrid.presentation.viewModel.shared.parser

import kotlinx.datetime.toLocalDate
import java.util.Locale

fun String.formatYearKtx(): String = runCatching {
    this.toLocalDate().year.toString()
}.getOrElse { "" }

fun String.formatFullDateKtx(): String = runCatching {
    val date = this.toLocalDate()
    val monthName = date.month.name.lowercase()
        .replaceFirstChar { it.uppercase() }
    val language = Locale.getDefault().toLanguageTag()
    if (language == "ar") {
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
        val months = monthsMap["ar"]!!
        val monthNameAr = months[date.monthNumber - 1]
        return convertEnglishDigitsToArabic("${date.dayOfMonth} $monthNameAr ${date.year}")
    }
    "$monthName ${date.dayOfMonth}, ${date.year}"
}.getOrElse { "" }
