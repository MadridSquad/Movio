package com.madrid.presentation.viewModel.shared.parser

fun formatDateOfBirth(rawDate: String): String {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    return try {
        val parts = rawDate.split("-")
        if (parts.size != 3) return "Unknown"

        val year = parts[0]
        val monthIndex = parts[1].toIntOrNull()?.minus(1) ?: return "Unknown"
        val day = parts[2].toIntOrNull() ?: return "Unknown"

        val monthName = months.getOrNull(monthIndex) ?: return "Unknown"
        "$monthName $day, $year"
    } catch (e: Exception) {
        "Unknown"
    }
}

