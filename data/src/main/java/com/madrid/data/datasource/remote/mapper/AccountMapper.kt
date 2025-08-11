package com.madrid.data.datasource.remote.mapper

import com.madrid.data.datasource.remote.dto.authentication.AccountDetailsResponse
import com.madrid.domain.entity.User

fun AccountDetailsResponse.toUser(): User {
    return User(
        id = this.id.toString(),
        username = this.username ?: this.name ?: "Unknown User",
        profilePicUrl =  "https://image.tmdb.org/t/p/original${this.avatar.tmdb.avatarPath}" ?: "",
        isGuest = false
    )
}