package com.madrid.presentation.viewModel.shared.parser

import kotlinx.datetime.toLocalDate

fun String.formatYearKtx(): String = runCatching {
    this.toLocalDate().year.toString()
}.getOrElse { "" }

fun String.formatFullDateKtx(): String = runCatching {
    val date = this.toLocalDate()
    val monthName = date.month.name.lowercase()
        .replaceFirstChar { it.uppercase() }
    "$monthName ${date.dayOfMonth}, ${date.year}"
}.getOrElse { "" }
