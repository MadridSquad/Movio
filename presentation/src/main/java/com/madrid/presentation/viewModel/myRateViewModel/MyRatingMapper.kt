package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase
import com.madrid.presentation.viewModel.shared.MediaType

fun GetUserRatedMovieUseCase.RatedMovie.toRatedMediaUiState(): RatedMediaState {
    return RatedMediaState(
        imageUrL = this.movie.imageUrl,
        mediaTitle = this.movie.title,
        rate = this.rate.toString(),
        mediaType = MediaType.MOVIE,
        mediaId = this.movie.id
    )
}

fun GetUserRatedSeriesUseCase.RatedSeries.toRatedMediaUiState(): RatedMediaState {
    return RatedMediaState(
        imageUrL = this.series.imageUrl,
        mediaTitle = this.series.title,
        rate = this.rate.toString(),
        mediaType = MediaType.TV_SHOW,
        mediaId = this.series.id
    )
}