package com.madrid.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.MovieWithGenres

@Dao
interface MovieDao {

    @Upsert
    suspend fun insertMovie(movie: MovieTable)

    @Delete
    suspend fun deleteMovie(movie: MovieTable)

    @Update
    suspend fun updateMovie(movie: MovieTable)

    @Query("SELECT * FROM MOVIE_TABLE WHERE movieId = :id")
    suspend fun getMovieById(id: Int): MovieTable?

    @Query("SELECT * FROM MOVIE_TABLE WHERE title LIKE :title LIMIT 20")
    suspend fun getMovieByTitle(title: String): List<MovieTable>

    @Query("SELECT * FROM MOVIE_TABLE ORDER BY rate DESC")
    suspend fun getTopRatedMovies(): List<MovieTable>

    @Query("SELECT * FROM MOVIE_TABLE")
    suspend fun getAllMovies(): List<MovieTable>

    @Query("DELETE FROM MOVIE_TABLE")
    suspend fun deleteAllMovies()

    // Genre
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenreCrossRef(crossRef: MovieGenreCrossRef)

    @Transaction
    @Query("""
    SELECT DISTINCT MOVIE_TABLE.* FROM MOVIE_TABLE
    INNER JOIN MovieGenreCrossRef ON MOVIE_TABLE.movieId = MovieGenreCrossRef.movieId
    INNER JOIN MOVIE_GENRE_TABLE ON MovieGenreCrossRef.genreId = MOVIE_GENRE_TABLE.genreId
    WHERE MOVIE_TABLE.title LIKE :title
    ORDER BY MOVIE_GENRE_TABLE.searchCount DESC
    LIMIT 20 OFFSET :offset 
    """)
    suspend fun searchMovies(title : String, offset: Int): List<MovieWithGenres>
}