package com.madrid.data.dataSource.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "RECENT_TABLE")
data class RecentSearchTable(
    @PrimaryKey(autoGenerate = false) val searchQuery: String,
    val timestamp: Long = System.currentTimeMillis()
)
