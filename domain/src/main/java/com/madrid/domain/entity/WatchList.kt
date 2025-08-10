package com.madrid.domain.entity

data class WatchList(
    val id: Int,
    val name: String,
    val itemCount: Int = 0,
    val description: String = "",
    val posterUrl: String? = null,
    var isSelected: Boolean = false,
    var isLoading: Boolean = false
)
