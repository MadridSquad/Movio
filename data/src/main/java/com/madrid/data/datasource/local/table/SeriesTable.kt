package com.madrid.data.datasource.local.table


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SERIES_TABLE")
data class SeriesTable (
    @PrimaryKey(autoGenerate = false) val seriesId: Int,
    val title: String,
    val imageUrl: String,
    val rate: Double,
    val yearOfRelease: String,
    val description: String,
)