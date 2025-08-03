package com.madrid.data.repositories.datasource

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getAuthToken(): Flow<String>
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun setAuthToken(token: String)
    suspend fun clearAuthToken()
    fun isGuest(): Flow<Boolean>
    suspend fun setIsGuest(isGuest: Boolean)
    fun isFirstLaunch(): Flow<Boolean>
    suspend fun setOnBoardingCompleted(isCompleted: Boolean)
}