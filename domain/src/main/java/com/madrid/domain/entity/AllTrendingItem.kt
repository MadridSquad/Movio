package com.madrid.domain.entity

data class AllTrendingItem(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val mediaType: String,
)