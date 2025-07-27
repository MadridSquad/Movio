package com.madrid.data.dataSource.local.entity.relationship

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.madrid.data.dataSource.local.entity.MovieGenreEntity
import com.madrid.data.dataSource.local.entity.MovieEntity

@Entity(tableName = "MovieGenreCrossRef", primaryKeys = ["genreId", "movieId"])
data class MovieGenreCrossRef(
    val genreId: Int,
    val movieId: Int
)

data class MovieWithGenres(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<MovieGenreEntity>
)

data class GenreWithMovies(
    @Embedded val genre: MovieGenreEntity,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<MovieEntity>
)