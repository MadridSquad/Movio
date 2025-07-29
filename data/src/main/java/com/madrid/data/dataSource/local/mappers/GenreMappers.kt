package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.remote.dto.genre.MovieGenre


fun MovieGenre.toMovieGenreTable(): MovieGenreTable {
    return MovieGenreTable(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        searchCount = 0
    )
}

fun MovieGenre.toSeriesGenreTable(): SeriesGenreTable {
    return SeriesGenreTable(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        searchCount = 0
    )
}