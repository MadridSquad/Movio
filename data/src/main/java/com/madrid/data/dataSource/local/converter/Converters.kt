package com.madrid.data.dataSource.local.converter

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromIntList(value: List<Int>): String? {
        return value.joinToString(",")
    }

    @TypeConverter
    fun stringToIntList(string: String?): List<Int> {
        try {
            if (string.isNullOrEmpty()) return emptyList()
            return string.removeSurrounding("[", "]")
                .replace(" ", "")
                .split(",")
                .map { it.toInt() }
        } catch (e: Exception) {
            return emptyList()
        }
    }
}