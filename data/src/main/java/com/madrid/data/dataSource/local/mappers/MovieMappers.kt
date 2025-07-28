package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.remote.response.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.response.movie.MovieResult
import com.madrid.domain.entity.Movie
import kotlinx.datetime.LocalDate


fun Movie.toMovieEntity(): MovieTable {
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

fun MovieDetailsResponse.toMovieEntity(): MovieTable {
    return MovieTable(
        movieId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = this.posterPath ?: "",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        movieDuration = (this.runtime ?: 0).toString(),
        description = this.overview ?: "",
    )
}

fun MovieResult.toMovieEntity(): MovieTable {
    return MovieTable(
        movieId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = ("https://image.tmdb.org/t/p/original" + this.posterPath) ?: "",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        movieDuration = 0.toString(),
        description = this.overview ?: "",
    )
}

