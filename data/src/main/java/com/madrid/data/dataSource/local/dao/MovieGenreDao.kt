package com.madrid.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.relationship.GenreWithMovies


@Dao
interface MovieGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: MovieGenreTable)

    @Update
    suspend fun updateGenre(genre: MovieGenreTable)

    @Query(
        """
        UPDATE MOVIE_GENRE_TABLE SET interestPoints = interestPoints + 1 
            WHERE genreTitle = :genreTitle
     """
    )
    suspend fun increaseGenreInterestPoints(genreTitle: String)

    @Delete
    suspend fun deleteGenre(genre: MovieGenreTable)

    @Query("SELECT * FROM MOVIE_GENRE_TABLE WHERE genreId = :id")
    suspend fun getGenreById(id: Int): MovieGenreTable?

    @Query("SELECT * FROM MOVIE_GENRE_TABLE WHERE genreTitle = :title")
    suspend fun getGenreByTitle(title: String): MovieGenreTable?

    @Query("SELECT * FROM MOVIE_GENRE_TABLE")
    suspend fun getAllGenres(): List<MovieGenreTable>

    // descending order by searchCount
    @Query("SELECT * FROM MOVIE_GENRE_TABLE ORDER BY interestPoints DESC")
    suspend fun getAllGenresBySearchCount(): List<MovieGenreTable>

    @Query("DELETE FROM MOVIE_GENRE_TABLE")
    suspend fun deleteAllGenres()

    @Transaction
    @Query("SELECT * FROM MOVIE_GENRE_TABLE")
    fun getMoviesByGenres(): List<GenreWithMovies>
}