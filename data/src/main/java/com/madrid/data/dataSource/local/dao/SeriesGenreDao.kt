package com.madrid.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.madrid.data.dataSource.local.entity.SeriesGenreEntity
import com.madrid.data.dataSource.local.entity.relationship.GenreWithSeries


@Dao
interface SeriesGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: SeriesGenreEntity)

    @Update
    suspend fun updateGenre(genre: SeriesGenreEntity)

    @Query(
        """
        UPDATE SERIES_GENRE_TABLE SET searchCount = searchCount + 1 
            WHERE genreTitle = :genreTitle
     """
    )
    suspend fun increaseGenreSearchCount(genreTitle: String)

    @Delete
    suspend fun deleteGenre(genre: SeriesGenreEntity)

    @Query("SELECT * FROM SERIES_GENRE_TABLE WHERE genreId = :id")
    suspend fun getGenreById(id: Int): SeriesGenreEntity?

    @Query("SELECT * FROM SERIES_GENRE_TABLE WHERE genreTitle = :title")
    suspend fun getGenreByTitle(title: String): SeriesGenreEntity?

    @Query("SELECT * FROM SERIES_GENRE_TABLE")
    suspend fun getAllGenres(): List<SeriesGenreEntity>

    // descending order by searchCount
    @Query("SELECT * FROM SERIES_GENRE_TABLE ORDER BY searchCount DESC")
    suspend fun getAllGenresBySearchCount(): List<SeriesGenreEntity>

    @Query("DELETE FROM SERIES_GENRE_TABLE")
    suspend fun deleteAllGenres()

    @Transaction
    @Query("SELECT * FROM SERIES_GENRE_TABLE")
    fun getSeriesByGenres(): List<GenreWithSeries>
}