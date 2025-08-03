package com.madrid.movio.di.hilt

import com.madrid.data.dataSource.encrypted.AuthenticationDatastoreImpl
import com.madrid.data.dataSource.local.LocalDataSourceImpl
import com.madrid.data.dataSource.remote.RemoteDataSourceImpl
import com.madrid.data.repositories.datasource.AuthenticationDataSource
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
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
    abstract fun bindAuthenticationDataSource(authenticationDataSource: AuthenticationDatastoreImpl): AuthenticationDataSource

}
