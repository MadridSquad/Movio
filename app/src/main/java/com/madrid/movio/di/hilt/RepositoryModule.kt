package com.madrid.movio.di.hilt

import android.content.Context
import com.madrid.data.dataSource.encrypted.AuthenticationDatastoreImpl
import com.madrid.data.dataSource.local.LocalDataSourceImpl
import com.madrid.data.dataSource.remote.RemoteDataSourceImpl
import com.madrid.data.repositories.ArtistRepositoryImpl
import com.madrid.data.repositories.MovieRepositoryImpl
import com.madrid.data.repositories.SearchRepositoryImpl
import com.madrid.data.repositories.SeriesRepositoryImpl
import com.madrid.data.repositories.datasource.AuthenticationDataSource
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.repository.ArtistRepository
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindArtistRepository(artistRepository: ArtistRepositoryImpl): ArtistRepository

    @Binds
    @Singleton
    abstract fun bindSeriesRepository(seriesRepository: SeriesRepositoryImpl): SeriesRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepository: SearchRepositoryImpl): UserRepository

   @Binds
    @Singleton
   abstract fun bindLocalDataSource(
      localDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract  fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Singleton
    @Provides
    fun provideAuthenticationDataSource(context: Context): AuthenticationDataSource {
        return AuthenticationDatastoreImpl(context = context)
    }
}