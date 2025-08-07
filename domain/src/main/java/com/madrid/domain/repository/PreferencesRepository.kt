package com.madrid.domain.repository

import com.madrid.domain.utils.AppLanguage
import com.madrid.domain.utils.AppTheme
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getAppDarkModeOn(): Flow<AppTheme>
    suspend fun setAppDarkModeOn(appTheme: AppTheme)

    fun getAppLanguage():  Flow<AppLanguage>
    suspend fun setAppLanguage(language: AppLanguage)
}