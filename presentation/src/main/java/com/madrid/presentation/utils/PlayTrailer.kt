package com.madrid.presentation.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsUiState

fun playSeriesTrailer(context: Context, uiState: SeriesDetailsUiState) {
    val trailerKey = uiState.trailerKey
    if (trailerKey.isNotEmpty()) {
        val youtubeAppIntent =
            Intent(Intent.ACTION_VIEW, "vnd.youtube:$trailerKey".toUri())
        val youtubeWebIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.youtube.com/watch?v=$trailerKey".toUri()
        )
        try {
            context.startActivity(youtubeAppIntent)
        } catch (e: Exception) {
            context.startActivity(youtubeWebIntent)

        }
    }
}