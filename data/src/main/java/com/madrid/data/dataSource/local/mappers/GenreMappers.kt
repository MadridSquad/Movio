package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.remote.response.genre.MovieGenre


fun MovieGenre.toMovieGenreEntity(): MovieGenreTable {
    return MovieGenreTable(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        searchCount = 0
    )
}

fun MovieGenre.toSeriesGenreEntity(): SeriesGenreTable {
    return SeriesGenreTable(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        searchCount = 0
    )
}