package com.madrid.presentation.viewModel.detailsViewModel.actor

data class NetworkDetailsUiState(
    val movieId : String ="",
    val cast: List<CastUiState> = emptyList(),
    val selectedActor: CastUiState? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
) {
    data class CastUiState(
        val actorImageUrl: String = "",
        val actorName: String = "",
        val actorRole: String = "",
        val dateOfBirth: String = "",
        val location: String = "location",
        val id: String = "",
        val description: String = "",
        val knownFor: List<KnownMovieUiState> = emptyList(),
        val errorMessage: String? = null
    )
    data class KnownMovieUiState(
        val title: String,
        val imageUrl: String,
        val rating: String,
        val mediaId: Int,
    )
}