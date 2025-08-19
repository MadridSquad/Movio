package com.madrid.data.repositories

import android.util.Log
import com.madrid.data.dataSource.remote.mapper.toUser
import com.madrid.data.repositories.datasource.UserPreferences
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.User
import com.madrid.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val authenticationDatasource: UserPreferences
) : AuthenticationRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Boolean {
        val userToken = remoteDataSource.getSessionId(username, password)
        authenticationDatasource.setAuthToken(userToken)
        authenticationDatasource.setIsGuest(false)
        return true
    }

    override suspend fun logout() {
        authenticationDatasource.clearAuthToken()
    }

    override suspend fun getCurrentUser(sessionId: String): User {
        return remoteDataSource.getCurrentUserDetails(sessionId).toUser()
    }

    override suspend fun getSessionId(): Flow<String> {
        return authenticationDatasource.getAuthToken()
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
       return authenticationDatasource.isUserLoggedIn()
    }

    override suspend fun loginAsGuest(): Boolean {
        val guest = remoteDataSource.loginAsGuest()
        authenticationDatasource.setAuthToken(guest)
        authenticationDatasource.setIsGuest(true)
        return true
    }

    override fun isGuest(): Flow<Boolean> {
        return authenticationDatasource.isGuest()
    }

    override fun isFirstLaunch(): Flow<Boolean> {
        return authenticationDatasource.isFirstLaunch()
    }

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        return authenticationDatasource.setOnBoardingCompleted(isCompleted = isCompleted)
    }
}