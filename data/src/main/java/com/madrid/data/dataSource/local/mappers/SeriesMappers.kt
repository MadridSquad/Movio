package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.local.table.relationship.SeriesWithGenres
import com.madrid.domain.entity.Series
import kotlinx.datetime.LocalDate

fun Series.toSeriesTable(): SeriesTable {
    return SeriesTable(
        seriesId = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = this.airDate.toString(),
        description = this.description,
    )
}

fun SeriesTable.toSeries(): Series {
    return Series(
        id = this.seriesId,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        airDate = LocalDate.parse(this.yearOfRelease).toString(),
        description = this.description,
        genre = listOf(),
        seasons = emptyList(),
    )
}

fun SeriesWithGenres.toSeries(): Series {
    return Series(
        id = this.series.seriesId,
        title = this.series.title,
        imageUrl = this.series.imageUrl,
        rate = this.series.rate,
        airDate = this.series.yearOfRelease,
        description = this.series.description,
        genre = this.genres.map { it.genreTitle },
        seasons = emptyList()
    )
}
