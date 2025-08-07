package com.madrid.domain.entity

data class WatchList(
    val id: Int,
    val name: String,
    val description: String? = null,
    val itemCount: Int = 0,
    val posterPath: String? = null
)
