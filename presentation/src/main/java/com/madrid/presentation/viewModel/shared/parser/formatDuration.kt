package com.madrid.presentation.viewModel.shared.parser


fun formatDuration(durationInMinutes: String): String {
    val minutes = durationInMinutes.toIntOrNull() ?: return "Unknown"
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return if (hours > 0) {
        "${hours}h ${remainingMinutes}min"
    } else {
        "${remainingMinutes}min"
    }
}