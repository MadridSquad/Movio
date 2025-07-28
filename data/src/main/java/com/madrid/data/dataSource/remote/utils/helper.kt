package com.madrid.data.dataSource.remote.utils

import com.madrid.domain.entity.Series

fun getDefaultSeries(): Series{
    return Series(
        id = 0,
        title = "",
        imageUrl = "",
        rate = 0.0,
        yearOfRelease = "",
        description = "",
        genre = listOf(),
    )
}