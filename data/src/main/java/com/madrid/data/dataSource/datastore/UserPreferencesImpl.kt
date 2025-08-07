package com.madrid.data.dataSource.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.madrid.data.repositories.datasource.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    override fun getAuthToken(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[TOKEN] ?: ""
            }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[TOKEN]?.isNotEmpty() ?: false
            }
    }

    override suspend fun setAuthToken(token: String) {
        dataStore.edit { settings ->
            settings[TOKEN] = token
        }
    }

    override suspend fun clearAuthToken() {
        dataStore.edit { settings ->
            settings[TOKEN] = ""
        }
    }

    override fun isGuest(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[IS_GUEST] ?: true
            }
    }

    override suspend fun setIsGuest(isGuest: Boolean) {
        dataStore.edit { settings ->
            settings[IS_GUEST] = isGuest
        }
    }

    override fun isFirstLaunch(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[ONBOARDING_COMPLETED]?.not() ?: true
        }
    }

    override suspend fun setOnBoardingCompleted(isCompleted: Boolean) {
        dataStore.edit { prefs ->
            prefs[ONBOARDING_COMPLETED] = isCompleted
        }
    }

    override fun getAppDarkModeOn(): Flow<Boolean> {
        return dataStore.data.map { settings ->
            settings[DARK_MODE] == true
        }
    }

    override suspend fun setAppDarkModeOn(isDarkMode: Boolean) {
        dataStore.edit { settings ->
            settings[DARK_MODE] = isDarkMode
        }
    }

    override fun getAppLanguage(): Flow<String> {
        return dataStore.data.map { settings ->
            settings[LANGUAGE] ?: ""
        }
    }

    override suspend fun setAppLanguage(language: String) {
        dataStore.edit { settings ->
            settings[LANGUAGE] = language
        }
    }

    companion object {
        val TOKEN = stringPreferencesKey("token") //TODO: Move to secrets
        val IS_GUEST = booleanPreferencesKey("is_guest")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
    }
}
