package com.madrid.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.madrid.data.dataSource.local.entity.MovieGenreEntity
import com.madrid.data.dataSource.local.entity.relationship.GenreWithMovies


@Dao
interface MovieGenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: MovieGenreEntity)

    @Update
    suspend fun updateGenre(genre: MovieGenreEntity)

    @Query(
        """
        UPDATE MOVIE_GENRE_TABLE SET searchCount = searchCount + 1 
            WHERE genreTitle = :genreTitle
     """
    )
    suspend fun increaseGenreSearchCount(genreTitle: String)

    @Delete
    suspend fun deleteGenre(genre: MovieGenreEntity)

    @Query("SELECT * FROM MOVIE_GENRE_TABLE WHERE genreId = :id")
    suspend fun getGenreById(id: Int): MovieGenreEntity?

    @Query("SELECT * FROM MOVIE_GENRE_TABLE WHERE genreTitle = :title")
    suspend fun getGenreByTitle(title: String): MovieGenreEntity?

    @Query("SELECT * FROM MOVIE_GENRE_TABLE")
    suspend fun getAllGenres(): List<MovieGenreEntity>

    // descending order by searchCount
    @Query("SELECT * FROM MOVIE_GENRE_TABLE ORDER BY searchCount DESC")
    suspend fun getAllGenresBySearchCount(): List<MovieGenreEntity>

    @Query("DELETE FROM MOVIE_GENRE_TABLE")
    suspend fun deleteAllGenres()

    @Transaction
    @Query("SELECT * FROM MOVIE_GENRE_TABLE")
    fun getMoviesByGenres(): List<GenreWithMovies>
}