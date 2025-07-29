package com.madrid.data.dataSource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ARTIST_TABLE")
data class ArtistTable(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val role: String,
    val dateOfBirth: String,
    val country: String,
    val description: String,
    val imageUrl: String,
)
