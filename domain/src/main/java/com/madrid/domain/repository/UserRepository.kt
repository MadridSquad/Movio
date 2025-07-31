package com.madrid.domain.repository

import com.madrid.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun register(email: String, password: String, username: String): User
    suspend fun logout()

    suspend fun getCurrentUser(): User?
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun refreshToken(): Boolean

    suspend fun sendPasswordResetEmail(email: String): Boolean

    suspend fun updateUserProfile(user: User): User
    suspend fun deleteAccount(): Boolean
    suspend fun isTokenExpired(token: String?): Boolean

    suspend fun loginAsGuest(): Boolean
}