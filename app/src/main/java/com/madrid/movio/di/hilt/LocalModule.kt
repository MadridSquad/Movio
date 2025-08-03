package com.madrid.movio.di.hilt

import android.content.Context
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
}