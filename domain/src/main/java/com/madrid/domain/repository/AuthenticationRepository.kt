package com.madrid.domain.repository

import com.madrid.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun logout()

    suspend fun getCurrentUser(sessionId: String): User?
    suspend fun getSessionId():  Flow<String>
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun loginAsGuest(): Boolean
    fun isGuest(): Flow<Boolean>

    fun isFirstLaunch(): Flow<Boolean>
    suspend fun setOnboardingCompleted(isCompleted: Boolean)
}