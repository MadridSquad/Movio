package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.domain.entity.Movie
import kotlinx.datetime.LocalDate


fun Movie.toMovieTable(): MovieTable {
    return MovieTable(
        movieId = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = this.yearOfRelease.toString(),
        movieDuration = this.movieDuration,
        description = this.description,
    )
}

fun MovieTable.toMovie(): Movie {
    return Movie(
        id = this.movieId,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = LocalDate.parse(this.yearOfRelease).toString(),
        movieDuration = this.movieDuration,
        description = this.description,
        genre = listOf(),
    )
}



