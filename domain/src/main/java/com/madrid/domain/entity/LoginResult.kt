package com.madrid.domain.entity

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val exception: Exception) : LoginResult()
}
