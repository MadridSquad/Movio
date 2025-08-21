package com.madrid.presentation.viewModel.detailsViewModel

import com.madrid.presentation.R

data class SeriesDetailsUiState(
    val seriesId: Int = 0,
    val seriesName: String = "",
    val description: String = "",
    val topImageUrl: String = "",
    val rate: String = "",
    val seriesGenre: List<String> = emptyList(),
    val numberOfSeasons: Int = 0,
    val productionDate: String = "",
    val topCast: List<ArtistUiState> = emptyList(),
    val currentSeasonsUiStates: List<SeasonUiState> = emptyList(),
    val reviews: List<ReviewUiState> = emptyList(),
    val similarSeries: List<SeriesUiState> = emptyList(),
    val selectedSeasonUiState: SeasonUiState = SeasonUiState(),
    val trailerKey: String = "",
    val isError: Boolean = false,
    val showLoadingScreen: Boolean = false,
    val isRated: Boolean = false,
    val isFavourite: Boolean = false,
    val isAddedToList: Boolean = false,
    val userRating: Int = 0,
    val isGuest: Boolean = true,
    val showSheet: Boolean = false,
    val showAddRatingBottomSheet: Boolean = false,
    val showDoneRatingBottomSheet: Boolean = false,
    val showAddToListBottomSheet: Boolean = false,
    val seeAllType: SeeAllType = SeeAllType.TopCast,
    val errorResMessageId: Int = R.string.feature_not_supported,
    val showSnackBar: Boolean = false,
    val isLoginBottomSheetVisible: Boolean = false
)

data class SeriesUiState(
    val id: Int = 0,
    val imageUrl: String = "",
    val rate: String = "",
    val name: String = "",
)

data class ArtistUiState(
    val id: Int,
    val imageUrl: String,
    val name: String
)

data class SeasonUiState(
    val id: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val seasonNumber: Int = 0,
    val productionDate: String = "",
    val numberOfEpisodes: Int = 0,
    val rate: String = "",
    val description: String = "",
    val episodesUiStates: List<EpisodeUiState> = listOf()
)

data class EpisodeUiState(
    val imageUrl: String = "",
    val episodeName: String = "",
    val episodeNumber: Int = 0,
    val episodeDuration: String = "",
    val rate: String = "",
    val trailerKey: String?
)

enum class SeeAllType(val stringResId: Int) {
    TopCast(R.string.top_cast),
    Season(R.string.current_seasons),
    Review(R.string.reviews),
    SimilarSeries(R.string.similar_series)
}