package com.madrid.data.dataSource.local.table.relationship

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieTable

@Entity(tableName = "MovieGenreCrossRef", primaryKeys = ["genreId", "movieId"])
data class MovieGenreCrossRef(
    val genreId: Int,
    val movieId: Int
)

data class MovieWithGenres(
    @Embedded val movie: MovieTable,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<MovieGenreTable>
)

data class GenreWithMovies(
    @Embedded val genre: MovieGenreTable,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<MovieTable>
)