package com.madrid.movio.di.hilt

import com.madrid.data.datasource.local.datastore.UserPreferencesImpl
import com.madrid.data.datasource.local.LocalDataSourceImpl
import com.madrid.data.datasource.remote.RemoteDataSourceImpl
import com.madrid.data.datasource.local.datastore.UserPreferences
import com.madrid.data.datasource.local.LocalDataSource
import com.madrid.data.datasource.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationDataSource(userPreferences: UserPreferencesImpl): UserPreferences
}