package com.madrid.data.dataSource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SERIES_GENRE_TABLE")
data class SeriesGenreTable(
    val genreTitle: String,
    @PrimaryKey(autoGenerate = false) val genreId: Int,
    val interestPoints: Int = 0,
)