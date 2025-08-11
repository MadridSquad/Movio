package com.madrid.data.datasource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MEDIA_HISTORY_TABLE")
data class MediaHistoryTable (
    @PrimaryKey(autoGenerate = false) val mediaId: Int,
    val mediaType: String,
    val addedAt: Long
)