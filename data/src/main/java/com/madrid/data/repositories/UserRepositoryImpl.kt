package com.madrid.data.repositories

import com.madrid.data.repositories.datasource.UserPreferences
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.User
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val authenticationDatasource: UserPreferences
) : UserRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Boolean {
       val userToken = remoteDataSource.login(username, password)
       authenticationDatasource.setAuthToken(userToken)
        return true
    }

    override suspend fun register(
        email: String,
        password: String,
        username: String
    ): User {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        authenticationDatasource.clearAuthToken()
    }

    override suspend fun getCurrentUser(): User? {
        TODO("Not yet implemented")
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
       return authenticationDatasource.isUserLoggedIn()
    }

    override suspend fun refreshToken(): Boolean {
        TODO("Not yet implemented")
    }


    override suspend fun sendPasswordResetEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserProfile(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isTokenExpired(token: String?): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loginAsGuest(): Boolean {
       val guest = remoteDataSource.loginAsGuest()
      authenticationDatasource.setAuthToken(guest)
        return true
    }

    override fun isFirstLaunch(): Flow<Boolean> {
        return authenticationDatasource.isFirstLaunch()
    }

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        return authenticationDatasource.setOnBoardingCompleted(isCompleted = isCompleted)
    }
}