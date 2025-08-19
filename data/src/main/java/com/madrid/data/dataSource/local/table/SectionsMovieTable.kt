package com.madrid.data.dataSource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SECTIONS_MOVIES")
data class SectionsMovieTable(
    @PrimaryKey(autoGenerate = false) val movieId: Int,
    val title: String,
    val imageUrl: String,
    val rate: Double,
    val yearOfRelease: String,
    val movieDuration: String,
    val description: String,
    val movieSection: String,
    val genresIds: List<Int>
)

enum class MovieSection(val value: String){
    TRENDING("trending"),
    NOW_PLAYING("now playing"),
    TOP_RATED("top rated"),
    UPCOMING("upcoming"),
    RECOMMENDED("recommended")
}

