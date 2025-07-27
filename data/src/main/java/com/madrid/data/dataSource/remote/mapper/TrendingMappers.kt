package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.response.trending.AllTrendingResponse
import com.madrid.data.dataSource.remote.response.trending.AllTrendingResponseItem
import com.madrid.domain.entity.AllTrendingItem

fun AllTrendingResponseItem.toAllTrending(): AllTrendingItem {
    return AllTrendingItem(
        id = this.id,
        title = this.title ?: this.name.orEmpty(),
        posterPath = "https://image.tmdb.org/t/p/original${this.posterPath ?: ""}",
        voteAverage = this.voteAverage,
        mediaType = this.mediaType
    )
}

fun AllTrendingResponse.toAllTrendingList(): List<AllTrendingItem> {
    return results.map { it.toAllTrending() }
}

