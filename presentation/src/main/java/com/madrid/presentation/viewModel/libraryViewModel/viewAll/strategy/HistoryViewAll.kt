package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.usecase.movie.AddMovieToHistoryUseCase
import com.madrid.domain.usecase.movie.DeleteMovieFromHistoryUseCase
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.series.AddSeriesToHistoryUseCase
import com.madrid.domain.usecase.series.DeleteSeriesFromHistoryUseCase
import com.madrid.domain.usecase.series.GetAllSeriesInHistoryUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import com.madrid.presentation.viewModel.shared.toMediaUiState
import javax.inject.Inject

class HistoryViewAll @Inject constructor(
    private val getMoviesHistoryUseCase: GetAllMoviesInHistoryUseCase,
    private val getSeriesHistoryUseCase: GetAllSeriesInHistoryUseCase,
    private val deleteHistoryUseCase: DeleteMovieFromHistoryUseCase,
    private val deleteSeriesFromHistoryUseCase: DeleteSeriesFromHistoryUseCase,
    private val addMovieToHistoryUseCase: AddMovieToHistoryUseCase,
    private val addSeriesToHistoryUseCase: AddSeriesToHistoryUseCase
) : ViewAllStrategy {

    override fun getTitle(): Int {
        return R.string.history
    }

    override fun getEmptyListMessage(): Int {
        return R.string.Start_watching_movies_and_shows
    }

    override suspend fun getAllItems(): List<MediaUiState> {
        return (getMoviesHistoryUseCase()
            .map { it.toMediaUiState() }
                + getSeriesHistoryUseCase()
            .map { it.toMediaUiState() })
    }

    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> deleteHistoryUseCase(mediaId.toInt())
            MediaType.TV_SHOW -> deleteSeriesFromHistoryUseCase(mediaId.toInt())
        }
    }

    override suspend fun onUndoDelete(
        mediaId: Int,
        mediaType: MediaType
    ) {
        when (mediaType) {
            MediaType.MOVIE -> addMovieToHistoryUseCase(
                movieId = mediaId
            )

            MediaType.TV_SHOW -> addSeriesToHistoryUseCase(
                seriesId = mediaId
            )
        }
    }
}