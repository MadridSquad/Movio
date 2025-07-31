package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.relationship.MovieWithGenres
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import kotlinx.datetime.LocalDate

fun Movie.toMovieTable(): MovieTable {
    return MovieTable(
        movieId = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        rate = this.rate,
        yearOfRelease = this.releaseDate.toString(),
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
        releaseDate = LocalDate.parse(this.yearOfRelease).toString(),
        movieDuration = this.movieDuration,
        description = this.description,
        genre = listOf(),
    )
}

fun MovieWithGenres.toMovie(): Movie {
    return Movie(
        id = this.movie.movieId,
        title = this.movie.title,
        imageUrl = this.movie.imageUrl,
        rate = this.movie.rate,
        releaseDate = this.movie.yearOfRelease,
        movieDuration = this.movie.movieDuration,
        description = this.movie.description,
        genre = this.genres.map { it.toGenre() }
    )
}





