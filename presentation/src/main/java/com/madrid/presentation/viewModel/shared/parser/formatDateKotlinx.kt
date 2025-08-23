package com.madrid.presentation.viewModel.shared.parser

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate


fun formatDateKotlinx(dateString: String): String {
    try{
        val date: LocalDate = dateString.toLocalDate()

        val month = date.monthNumber.toString().padStart(2, '0')
        val day = date.dayOfMonth.toString().padStart(2, '0')
        val year = date.year.toString()

        return "$month/$day/$year"
    }
    catch (e: Exception) {
        return ""
    }
}