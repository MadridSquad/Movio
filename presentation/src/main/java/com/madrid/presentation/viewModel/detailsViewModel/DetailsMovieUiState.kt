package com.madrid.presentation.viewModel.detailsViewModel

import com.madrid.domain.entity.Artist
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie

data class DetailsMovieUiState(
    val isLoved: Boolean = false,
    val topImageUrl: String = "",
    val movieName: String = "",
    val movieId: Int = 0,
    val genreMovie: List<String> = emptyList(),
    val rate: String = "",
    val movieDuration: String = "",
    val dataMovie: String = "",
    val isLoading: Boolean = false,
    val isRated: Boolean = false,
    val isAddedToList: Boolean = false,
    val userRating: Int = 0,
    val isGuest: Boolean = true,
    val description: String = "",
    val casts: List<Artist> = emptyList(),
    val reviews: List<ReviewUiState> = emptyList(),

    val similarMovies: List<SimilarMovie> = emptyList(),
    val trailerKey: String = "",
    val isLoginBottomSheetVisible: Boolean = false
)