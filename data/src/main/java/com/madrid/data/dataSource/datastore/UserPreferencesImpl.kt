package com.madrid.data.dataSource.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.madrid.data.repositories.datasource.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(
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

    companion object {
        val TOKEN = stringPreferencesKey("token") //TODO: Move to secrets
    }
}
