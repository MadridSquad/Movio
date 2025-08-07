package com.madrid.movio.di.hilt

import com.madrid.data.repositories.ArtistRepositoryImpl
import com.madrid.data.repositories.AuthenticationRepositoryImpl
import com.madrid.data.repositories.MovieRepositoryImpl
import com.madrid.data.repositories.PreferencesRepositoryImpl
import com.madrid.data.repositories.SearchRepositoryImpl
import com.madrid.data.repositories.SeriesRepositoryImpl
import com.madrid.domain.repository.ArtistRepository
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
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
    abstract fun bindAuthenticationRepository(authenticationRepository: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository

}