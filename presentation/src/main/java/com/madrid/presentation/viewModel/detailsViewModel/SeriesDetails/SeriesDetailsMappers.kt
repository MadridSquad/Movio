package com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Season
import com.madrid.domain.entity.Series
import com.madrid.presentation.utils.formatRate
import com.madrid.presentation.viewModel.detailsViewModel.ArtistUiState
import com.madrid.presentation.viewModel.detailsViewModel.EpisodeUiState
import com.madrid.presentation.viewModel.detailsViewModel.ReviewUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeasonUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesUiState

fun Episode.toUiState(): EpisodeUiState {
    return EpisodeUiState(
        imageUrl = this.imageUrl,
        episodeName = this.title,
        episodeNumber = this.episodeNumber,
        episodeDuration = this.duration,
        rate = this.rate.toString(),
        trailerKey = this.trailer.toString(),
    )
}

fun Season.toUiState(): SeasonUiState {
    return SeasonUiState(
        id = this.id,
        title = title,
        imageUrl = this.imageUrl,
        seasonNumber = this.seasonNumber,
        productionDate = this.date,
        rate = this.rate.toString(),
        description = this.description,
    )
}

fun Artist.toUiState(): ArtistUiState {
    return ArtistUiState(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name
    )
}

fun Series.toUiState(): SeriesUiState {
    return SeriesUiState(
        id = this.id,
        name = this.title,
        imageUrl = this.imageUrl,
        rate = formatRate(this.rate)
    )
}

fun Review.toUiState(): ReviewUiState {
    return ReviewUiState(
        reviewerName = this.reviewerName,
        reviewerImageUrl = this.reviewerPhotoUrl,
        rating = this.rate.toFloat(),
        date = (this.date),
        content = this.comment
    )
}