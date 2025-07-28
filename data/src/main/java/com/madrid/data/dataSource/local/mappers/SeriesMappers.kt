package com.madrid.data.dataSource.local.mappers


import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.remote.response.series.SeriesResult
import com.madrid.domain.entity.Series
import kotlinx.datetime.LocalDate

fun Series.toSeriesEntity(): SeriesTable {
    return SeriesTable(
        seriesId = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = this.yearOfRelease.toString(),
        description = this.description,
    )
}

fun SeriesTable.toSeries(): Series {
    return Series(
        id = this.seriesId,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = LocalDate.parse(this.yearOfRelease).toString(),
        description = this.description,
        genre = listOf(),

        )
}

fun SeriesResult.toSeriesEntity(): SeriesTable {
    return SeriesTable(
        seriesId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = ("https://image.tmdb.org/t/p/original" + this.posterPath) ?: "",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        description = this.overview ?: ""
    )
}