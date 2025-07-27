package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.entity.MovieGenreEntity
import com.madrid.data.dataSource.local.entity.SeriesGenreEntity
import com.madrid.data.dataSource.remote.response.genre.MovieGenre


fun MovieGenre.toMovieGenreEntity(): MovieGenreEntity {
    return MovieGenreEntity(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        searchCount = 0
    )
}

fun MovieGenre.toSeriesGenreEntity(): SeriesGenreEntity {
    return SeriesGenreEntity(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        searchCount = 0
    )
}