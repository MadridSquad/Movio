package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.entity.RatedMovie
import com.madrid.domain.entity.RatedSeries

fun RatedMovie.toRatedMovieUiState(): RatedMovieState {
    return RatedMovieState(
        imageUrL = this.movie.imageUrl,
        mediaTitle = this.movie.title,
        rate = this.rate.toString()
    )
}

fun RatedSeries.toRatedSeriesUiState(): RatedSeriesState {
    return RatedSeriesState(
        imageUrL = this.series.imageUrl,
        mediaTitle = this.series.title,
        rate = this.rate.toString()
    )
}