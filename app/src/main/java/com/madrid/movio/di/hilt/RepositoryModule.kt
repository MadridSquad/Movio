package com.madrid.movio.di.hilt

import com.madrid.data.repositories.ArtistRepositoryImpl
import com.madrid.data.repositories.MovieRepositoryImpl
import com.madrid.data.repositories.SearchRepositoryImpl
import com.madrid.data.repositories.SeriesRepositoryImpl
import com.madrid.data.repositories.UserRepositoryImpl
import com.madrid.domain.repository.ArtistRepository
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
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
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}