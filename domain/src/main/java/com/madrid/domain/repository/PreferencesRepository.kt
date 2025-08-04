package com.madrid.domain.repository

import com.madrid.domain.utils.Theme
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getAppDarkModeOn(): Flow<Theme>
    suspend fun setAppDarkModeOn(theme: Theme)

    suspend fun getAppLanguage(): String
    suspend fun setAppLanguage(language: String)
}