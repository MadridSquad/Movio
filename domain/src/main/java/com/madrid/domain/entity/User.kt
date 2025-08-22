package com.madrid.domain.entity
data class User(
    val id: String,
    val username: String,
    val profilePicUrl: String?,
    val isGuest: Boolean = false
)