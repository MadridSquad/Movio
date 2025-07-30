package com.madrid.data.dataSource.mapper

import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.remote.dto.genre.RemoteGenreDto

fun RemoteGenreDto.toMovieGenreTable(): MovieGenreTable {
    return MovieGenreTable(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        interestPoints = 0
    )
}

fun RemoteGenreDto.toSeriesGenreTable(): SeriesGenreTable {
    return SeriesGenreTable(
        genreId = this.id ?: 0,
        genreTitle = this.name ?: "Unknown",
        interestPoints = 0
    )
}