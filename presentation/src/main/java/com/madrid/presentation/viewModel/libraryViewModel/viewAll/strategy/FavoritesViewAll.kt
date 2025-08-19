package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.domain.usecase.movie.SetMovieFavoriteStatusUseCase
import com.madrid.domain.usecase.series.GetFavoriteSeriesUseCase
import com.madrid.domain.usecase.series.SetSeriesFavoriteStatusUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import com.madrid.presentation.viewModel.shared.toMediaUiState
import javax.inject.Inject

class FavoritesViewAll @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase,
    private val setMovieFavoriteStatusUseCase: SetMovieFavoriteStatusUseCase,
    private val setSeriesFavoriteStatusUseCase: SetSeriesFavoriteStatusUseCase
) : ViewAllStrategy {

    override fun getTitle(): Int {
        return R.string.favorites
    }

    override fun getEmptyListMessage(): Int {
        return R.string.Start_adding_the_movies_and_shows_you_love
    }

    override suspend fun getAllItems(): List<MediaUiState> {
        return (getFavoriteMoviesUseCase().map { it.toMediaUiState() } +
                getFavoriteSeriesUseCase().map { it.toMediaUiState() })
    }


    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> setMovieFavoriteStatusUseCase(
                movieId = mediaId.toInt(),
                isFavorite = false
            )

            MediaType.TV_SHOW -> setSeriesFavoriteStatusUseCase(
                seriesId = mediaId.toInt(),
                isFavorite = false
            )
        }
    }

    override suspend fun onUndoDelete(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> setMovieFavoriteStatusUseCase(
                movieId = mediaId,
                isFavorite = true
            )

            MediaType.TV_SHOW -> setSeriesFavoriteStatusUseCase(
                seriesId = mediaId,
                isFavorite = true
            )
        }
    }

}