package com.madrid.data.dataSource.local.mappers


import com.madrid.data.dataSource.local.entity.SeriesEntity
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.domain.entity.Series
import kotlinx.datetime.LocalDate

fun Series.toSeriesEntity(): SeriesEntity {
    return SeriesEntity(
        seriesId = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = this.airDate.toString(),
        description = this.description,
    )
}

fun SeriesEntity.toSeries(): Series {
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

fun SeriesResult.toSeriesEntity(): SeriesEntity {
    return SeriesEntity(
        seriesId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = ("https://image.tmdb.org/t/p/original" + this.posterPath),
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        description = this.overview ?: ""
    )
}