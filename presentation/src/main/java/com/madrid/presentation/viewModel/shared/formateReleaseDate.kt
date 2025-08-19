package com.madrid.presentation.viewModel.shared


fun formatDate(dateString: String): String {
    if (dateString.isBlank()) return "Unknown"

    return try {
        val parts = dateString.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        "$day/$month/$year"
    } catch (e: Exception) {
        val parts = dateString.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        "$month/$day/$year"

    }
}