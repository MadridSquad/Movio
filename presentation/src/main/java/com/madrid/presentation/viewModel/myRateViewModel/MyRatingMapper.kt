package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.movie.GetUserRatedMovie
import com.madrid.domain.usecase.series.GetUserRatedSeries
import com.madrid.presentation.viewModel.shared.MediaType

fun GetUserRatedMovie.RatedMovie.toRatedMediaUiState(): RatedMediaState {
    return RatedMediaState(
        imageUrL = this.movie.imageUrl,
        mediaTitle = this.movie.title,
        rate = this.rate.toString(),
        mediaType = MediaType.MOVIE
    )
}

fun GetUserRatedSeries.RatedSeries.toRatedMediaUiState(): RatedMediaState {
    return RatedMediaState(
        imageUrL = this.series.imageUrl,
        mediaTitle = this.series.title,
        rate = this.rate.toString(),
        mediaType = MediaType.TV_SHOW
    )
}