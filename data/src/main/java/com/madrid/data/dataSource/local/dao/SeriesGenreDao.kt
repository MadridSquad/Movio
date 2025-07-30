package com.madrid.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.local.table.relationship.GenreWithSeries


@Dao
interface SeriesGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: SeriesGenreTable)

    @Update
    suspend fun updateGenre(genre: SeriesGenreTable)

    @Query(
        """
        UPDATE SERIES_GENRE_TABLE SET interestPoints = interestPoints + 1 
            WHERE genreTitle = :genreTitle
     """
    )
    suspend fun increaseGenreInterestPoints(genreTitle: String)

    @Delete
    suspend fun deleteGenre(genre: SeriesGenreTable)

    @Query("SELECT * FROM SERIES_GENRE_TABLE WHERE genreId = :id")
    suspend fun getGenreById(id: Int): SeriesGenreTable?

    @Query("SELECT * FROM SERIES_GENRE_TABLE WHERE genreTitle = :title")
    suspend fun getGenreByTitle(title: String): SeriesGenreTable?

    @Query("SELECT * FROM SERIES_GENRE_TABLE")
    suspend fun getAllGenres(): List<SeriesGenreTable>

    // descending order by searchCount
    @Query("SELECT * FROM SERIES_GENRE_TABLE ORDER BY interestPoints DESC")
    suspend fun getAllGenresBySearchCount(): List<SeriesGenreTable>

    @Query("DELETE FROM SERIES_GENRE_TABLE")
    suspend fun deleteAllGenres()

    @Transaction
    @Query("SELECT * FROM SERIES_GENRE_TABLE")
    fun getSeriesByGenres(): List<GenreWithSeries>
}