package com.madrid.data.dataSource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOVIE_TABLE")
data class MovieTable(
    @PrimaryKey(autoGenerate = false) val movieId: Int,
    val title: String,
    val imageUrl: String,
    val rate: Double,
    val yearOfRelease: String,
    val movieDuration: String,
    val description: String,
)