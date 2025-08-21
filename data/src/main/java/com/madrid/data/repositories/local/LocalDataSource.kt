package com.madrid.data.repositories.local

import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.data.dataSource.local.table.MediaHistoryTable
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.RecentSearchTable
import com.madrid.data.dataSource.local.table.SectionsMovieTable
import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.local.table.relationship.GenreWithMovies
import com.madrid.data.dataSource.local.table.relationship.GenreWithSeries
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.MovieWithGenres
import com.madrid.data.dataSource.local.table.relationship.SeriesGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.SeriesWithGenres

interface LocalDataSource {

    suspend fun insertMovie(movie: MovieTable)
    suspend fun insertSectionMovie(movie: SectionsMovieTable)
    suspend fun insertSeries(series: SeriesTable)
    suspend fun insertArtist(artist: ArtistTable)
    suspend fun insertMovieGenre(genre: MovieGenreTable)
    suspend fun insertSeriesGenre(genre: SeriesGenreTable)
    suspend fun clearMovieGenres()
    suspend fun clearSeriesGenres()

    suspend fun getTopRatedMovies(): List<MovieTable>

    suspend fun searchMovieByQueryFromDB(query: String, page: Int): List<MovieWithGenres>
    suspend fun searchSeriesByQueryFromDB(query: String, page: Int): List<SeriesWithGenres>
    suspend fun searchArtistByQueryFromDB(query: String, page: Int): List<ArtistTable>

    suspend fun getRecentSearches(): List<RecentSearchTable>
    suspend fun addRecentSearch(item: String)
    suspend fun removeRecentSearch(item: String)
    suspend fun clearAllRecentSearches()

    suspend fun relateMovieToGenre(movieGenreCrossRef: MovieGenreCrossRef)
    suspend fun increaseMovieGenreInterestPoints(genreTitle: String)

    suspend fun relateSeriesToGenre(seriesGenreEntity: SeriesGenreCrossRef)
    suspend fun increaseSeriesGenreInterestPoints(genreTitle: String)

    suspend fun getAllMovieGenres(): List<MovieGenreTable>
    suspend fun getAllSeriesGenres(): List<SeriesGenreTable>

    suspend fun getMoviesByGenres(): List<GenreWithMovies>
    suspend fun getSeriesByGenres(): List<GenreWithSeries>

    suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreTable>
    suspend fun getSeriesGenresByIds(ids: List<Int>): List<SeriesGenreTable>

    suspend fun getNowPlayingMovies(): List<SectionsMovieTable>
    suspend fun getUpComingMovies(): List<SectionsMovieTable>
    suspend fun getTrendingMovies(): List<SectionsMovieTable>
    suspend fun getTopRatingMovies(): List<SectionsMovieTable>
    suspend fun getRecommendedMovies(): List<SectionsMovieTable>

    suspend fun clearHomeMoviesCache()
    suspend fun addMovieToHistory(movieId: Int,userId: Int)
    suspend fun deleteMovieFromHistory(movieId: Int,userId: Int)
    suspend fun addSeriesToHistory(seriesId: Int,userId: Int)
    suspend fun deleteSeriesFromHistory(seriesId: Int,userId: Int)
    suspend fun getAllMoviesInHistory(userId: Int): List<MediaHistoryTable>
    suspend fun getAllSeriesInHistory(userId: Int): List<MediaHistoryTable>
}