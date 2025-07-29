package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.entity.MovieEntity
import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.domain.entity.Movie
import kotlinx.datetime.LocalDate

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        movieId = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = this.releaseDate.toString(),
        movieDuration = this.movieDuration,
        description = this.description,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.movieId,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        releaseDate = LocalDate.parse(this.yearOfRelease).toString(),
        movieDuration = this.movieDuration,
        description = this.description,
        genre = listOf(),
    )
}

fun MovieResult.toMovieEntity(): MovieEntity {
    return MovieEntity(
        movieId = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = ("https://image.tmdb.org/t/p/original" + this.posterPath),
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        movieDuration = 0.toString(),
        description = this.overview ?: "",
    )
}

