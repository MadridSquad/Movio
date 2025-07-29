package com.madrid.data.dataSource.mapper

import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.remote.response.series.SeriesResult


fun SeriesResult.toSeriesTable(): SeriesTable {
    return SeriesTable(
        seriesId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = ("https://image.tmdb.org/t/p/original" + this.posterPath) ?: "",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        description = this.overview ?: ""
    )
}