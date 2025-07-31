package com.madrid.domain.entity

import com.madrid.domain.entity.User

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val exception: Exception) : LoginResult()
}
