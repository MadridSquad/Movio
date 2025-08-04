package com.madrid.presentation.viewModel.detailsViewModel

import android.util.Log

private const val TAG = "SimilarMediaUiState"

data class SimilarMediaUiState(
    val isMovie: Boolean = true,
    val headerName: String = "",
    val medias: List<MediaUiState> = emptyList()
) {
    init {
        Log.d(TAG, buildString {
            append("SimilarMediaUiState [${if (isMovie) "Movies" else "TV Shows"}]: ")
            append("\"$headerName\" (${medias.size} items)")

            if (medias.isNotEmpty()) {
                append("\nFirst 3 items:")
                medias.take(3).forEachIndexed { index, media ->
                    append("\n${index + 1}. ${media.toLogString()}")
                }

                if (medias.size > 3) {
                    append("\n...and ${medias.size - 3} more")
                }
            }
        })
    }
}

data class MediaUiState(
    val mediaId: Int = 0,
    val isMovie: Boolean = true,
    val imageUrl: String = "",
    val mediaName: String = "",
    val rate: String = ""
) {
    fun toLogString(): String {
        return buildString {
            append(mediaName.take(20).padEnd(22))
            append("ID:$mediaId".padEnd(10))
            append("Type:${if (isMovie) "Movie" else "TV".padEnd(6)}")
            if (rate.isNotBlank()) {
                append("Rating:$rate")
            }
        }
    }

    fun logMediaClick() {
        Log.d(TAG, "Clicked: ${toLogString()}")
    }
}