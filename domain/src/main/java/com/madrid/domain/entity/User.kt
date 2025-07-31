package com.madrid.domain.entity
data class User(
    val id: String,
    val username: String,
    val email: String?,
    val profilePicUrl: String?,
    val authToken: String?,
    val isGuest: Boolean = false
)