package com.madrid.movio.di.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.madrid.data.dataSource.local.dao.ArtistDao
import com.madrid.data.dataSource.local.dao.MovieDao
import com.madrid.data.dataSource.local.dao.MovieGenreDao
import com.madrid.data.dataSource.local.dao.RecentSearchDao
import com.madrid.data.dataSource.local.dao.SeriesDao
import com.madrid.data.dataSource.local.dao.SeriesGenreDao
import com.madrid.data.dataSource.local.database.MovioDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMovioDatabase(@ApplicationContext context: Context): MovioDatabase {
        return MovioDatabase.getInstance(context)
    }

    @Provides
    fun provideMovieDao(database: MovioDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideSeriesDao(database: MovioDatabase): SeriesDao {
        return database.seriesDao()
    }

    @Provides
    fun provideMovieGenreDao(database: MovioDatabase): MovieGenreDao {
        return database.movieGenreDao()
    }

    @Provides
    fun provideSeriesGenreDao(database: MovioDatabase): SeriesGenreDao {
        return database.seriesGenreDao()
    }

    @Provides
    fun provideArtistDao(database: MovioDatabase): ArtistDao {
        return database.artistDao()
    }

    @Provides
    fun provideRecentSearchDao(database: MovioDatabase): RecentSearchDao {
        return database.recentSearchDao()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}