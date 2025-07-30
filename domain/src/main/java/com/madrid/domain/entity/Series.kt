package com.madrid.domain.entity

data class Series(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rate: Double,
    val airDate: String,
    val seasons: List<Season>,
    val description: String,
    val genre: List<String>,
)