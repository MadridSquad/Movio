package com.madrid.data.dataSource.encrypted

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.madrid.data.repositories.datasource.AuthenticationDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticationDatastoreImpl @Inject constructor(
    private val context: Context
) : AuthenticationDataSource {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings") //TODO: Move to secrets

    val TOKEN = stringPreferencesKey("token") //TODO: Move to secrets

    override fun getAuthToken(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN] ?: ""
            }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN]?.isNotEmpty() ?: false
            }
    }

    override suspend fun setAuthToken(token: String) {
        context.dataStore.edit { settings ->
            settings[TOKEN] = token
        }
    }

    override suspend fun clearAuthToken() {
        context.dataStore.edit { settings ->
            settings[TOKEN] = ""
        }
    }
}
