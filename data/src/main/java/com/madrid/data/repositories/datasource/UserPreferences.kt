package com.madrid.data.repositories.datasource

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getAuthToken(): Flow<String> //get session id
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun setAuthToken(token: String)
    suspend fun clearAuthToken()
    fun isFirstLaunch(): Flow<Boolean>
    suspend fun setOnBoardingCompleted(isCompleted: Boolean)

    fun isGuest(): Flow<Boolean>
    suspend fun setIsGuest(isGuest: Boolean)

    fun getAppDarkModeOn(): Flow<Boolean>
    suspend fun setAppDarkModeOn(isDarkMode: Boolean)

    fun getAppLanguage(): Flow<String>
    suspend fun setAppLanguage(language: String)
}