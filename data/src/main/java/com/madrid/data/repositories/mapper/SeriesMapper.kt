package com.madrid.data.repositories.mapper

import com.madrid.data.datasource.local.table.SeriesTable
import com.madrid.data.datasource.remote.dto.series.SeriesResult

fun SeriesResult.toSeriesTable(): SeriesTable {
    return SeriesTable(
        seriesId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = ("https://image.tmdb.org/t/p/original" + this.posterPath),
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        description = this.overview ?: ""
    )
}