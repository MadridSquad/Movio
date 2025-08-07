package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase

fun MovieResult.toRatedMovie(): GetUserRatedMovieUseCase.RatedMovie {
    return GetUserRatedMovieUseCase.RatedMovie(
        rate = this.rating ?: 0.0,
        movie = this.toMovie()
    )
}

fun SeriesResult.toRatedSeries(): GetUserRatedSeriesUseCase.RatedSeries {
    return GetUserRatedSeriesUseCase.RatedSeries(
        rate = this.rating ?: 0.0,
        series = this.toSeries()
    )
}

