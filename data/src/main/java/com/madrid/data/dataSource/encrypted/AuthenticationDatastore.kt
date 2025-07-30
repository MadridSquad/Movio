package com.madrid.data.dataSource.encrypted

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthenticationDatastore(
    private val context: Context
) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val TOKEN = stringPreferencesKey("token")

    fun getAuthToken(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN] ?: ""
            }
    }

    suspend fun setAuthToken(token: String) {
        context.dataStore.edit { settings ->
            settings[TOKEN] = token
        }
    }

    suspend fun clearAuthToken() {
        context.dataStore.edit { settings ->
            settings[TOKEN] = ""
        }
    }
}
