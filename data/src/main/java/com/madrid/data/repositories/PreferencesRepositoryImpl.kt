package com.madrid.data.repositories

import com.madrid.data.repositories.datasource.UserPreferences
import com.madrid.data.repositories.mapper.toBoolean
import com.madrid.data.repositories.mapper.toLanguage
import com.madrid.data.repositories.mapper.toStringLanguage
import com.madrid.data.repositories.mapper.toTheme
import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppLanguage
import com.madrid.domain.utils.AppTheme
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl@Inject constructor(
    private val userPreferences: UserPreferences
) : PreferencesRepository {
    override fun getAppDarkModeOn(): Flow<AppTheme> {
        return userPreferences.getAppDarkModeOn().toTheme()
    }

    override suspend fun setAppDarkModeOn(appTheme: AppTheme) {
        userPreferences.setAppDarkModeOn(appTheme.toBoolean())
    }

    override fun getAppLanguage(): Flow<AppLanguage> {
        return userPreferences.getAppLanguage().toLanguage()
    }

    override suspend fun setAppLanguage(language: AppLanguage) {
        userPreferences.setAppLanguage(language.toStringLanguage())
    }
}