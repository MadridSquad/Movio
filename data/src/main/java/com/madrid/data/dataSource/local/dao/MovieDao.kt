package com.madrid.data.dataSource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.madrid.data.dataSource.local.table.MovieSection
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.SectionsMovieTable
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.MovieWithGenres
import com.madrid.data.dataSource.local.util.PAGE_SIZE

@Dao
interface MovieDao {

    @Upsert
    suspend fun insertMovie(movie: MovieTable)

    @Upsert
    suspend fun insertSectionMovie(movie: SectionsMovieTable)

    @Delete
    suspend fun deleteMovie(movie: MovieTable)

    @Query("DELETE FROM SECTIONS_MOVIES")
    suspend fun clearHomeMoviesCache()

    @Update
    suspend fun updateMovie(movie: MovieTable)

    @Query("SELECT * FROM MOVIE_TABLE WHERE movieId = :id")
    suspend fun getMovieById(id: Int): MovieTable?

    @Query("SELECT * FROM MOVIE_TABLE WHERE title LIKE :title LIMIT $PAGE_SIZE")
    suspend fun getMovieByTitle(title: String): List<MovieTable>

    @Query("SELECT * FROM MOVIE_TABLE ORDER BY rate DESC")
    suspend fun getTopRatedMovies(): List<MovieTable>

    @Query("SELECT * FROM SECTIONS_MOVIES WHERE movieSection = :section")
    suspend fun getMoviesBySection(section: String): List<MovieTable>

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
    LIMIT $PAGE_SIZE OFFSET :offset 
    """)
    suspend fun searchMovies(title : String, offset: Int): List<MovieWithGenres>
}