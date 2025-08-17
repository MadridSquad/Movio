package com.madrid.domain.entity

data class Season(
    val id: Int,
    val title:String,
    val seasonNumber: Int,
    val imageUrl: String,
    val rate: Double,
    val date: String,
    val description: String,
    val episodeCount: Int
)