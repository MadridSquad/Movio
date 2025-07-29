package com.madrid.data.dataSource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOVIE_GENRE_TABLE")
data class MovieGenreTable(
    val genreTitle: String,
    @PrimaryKey(autoGenerate = false)  val genreId: Int,
    val searchCount: Int = 0,
)
