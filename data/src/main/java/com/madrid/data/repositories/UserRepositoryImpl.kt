package com.madrid.data.repositories

import com.madrid.data.dataSource.encrypted.AuthenticationDatastore
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.User
import com.madrid.domain.repository.UserRepository

class UserRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val authenticationDatastore: AuthenticationDatastore
) : UserRepository {

    override suspend fun login(
        username: String,
        password: String
    ): User {
        val userToken = remoteDataSource.login(username, password)
        authenticationDatastore.setAuthToken(userToken)
        return User(
            id = "12345",
            username = username,
            email = null,
            profilePicUrl = null,
            authToken = userToken,
            isGuest = false
        )
    }

    override suspend fun register(
        email: String,
        password: String,
        username: String
    ): User {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        authenticationDatastore.clearAuthToken()
    }

    override suspend fun getCurrentUser(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
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

    override suspend fun loginAsGuest(): User {
        val guest = remoteDataSource.loginAsGuest()
        authenticationDatastore.setAuthToken(guest)
        return User(
            id = "guest",
            username = "Guest",
            email = null,
            profilePicUrl = null,
            authToken = guest,
            isGuest = true
        )
    }
}