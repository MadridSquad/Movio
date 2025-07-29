package com.madrid.data.dataSource.mapper

import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.remote.response.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.response.movie.MovieResult


fun MovieResult.toMovieTable(): MovieTable {
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

fun MovieDetailsResponse.toMovieTable(): MovieTable {
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