package com.madrid.data.dataSource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.madrid.data.dataSource.local.dao.ArtistDao
import com.madrid.data.dataSource.local.dao.MovieDao
import com.madrid.data.dataSource.local.dao.MovieGenreDao
import com.madrid.data.dataSource.local.dao.RecentSearchDao
import com.madrid.data.dataSource.local.dao.SeriesDao
import com.madrid.data.dataSource.local.dao.SeriesGenreDao
import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.data.dataSource.local.table.MediaHistoryTable
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.RecentSearchTable
import com.madrid.data.dataSource.local.table.SectionsMovieTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.SeriesGenreCrossRef

@Database(
    entities = [
        MovieTable::class,
        SeriesTable::class,
        MovieGenreTable::class,
        SeriesGenreTable::class,
        ArtistTable::class,
        RecentSearchTable::class,
        MovieGenreCrossRef::class,
        SeriesGenreCrossRef::class,
        SectionsMovieTable::class,
        MediaHistoryTable::class
    ],
    version = 1
)
abstract class MovioDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun movieGenreDao(): MovieGenreDao
    abstract fun seriesGenreDao(): SeriesGenreDao
    abstract fun artistDao(): ArtistDao
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        const val DATABASE_NAME = "MOVIO_DATABASE"

        @Volatile
        private var instance: MovioDatabase? = null

        fun getInstance(context: Context): MovioDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        fun getInstanceWithoutContext(): MovioDatabase {
            return instance
                ?: throw IllegalStateException("Database not initialized. Call getInstance(context) first.")
        }

        private fun buildDatabase(context: Context): MovioDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MovioDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration(true) //TODO: Check Migration strategy
                .build()
        }
    }
}