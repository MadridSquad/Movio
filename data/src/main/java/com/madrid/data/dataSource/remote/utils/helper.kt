package com.madrid.data.dataSource.remote.utils

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series

fun getDefaultMovie(): Movie{
    return Movie(
        id = 0,
        title = "",
        imageUrl = "",
        rate = 0.0,
        yearOfRelease = "",
        description = "",
        genre = listOf(),
        movieDuration = "",
        crew = listOf(),
        profilePage = " "
    )
}

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