package com.madrid.data.repositories

import com.madrid.data.dataSource.local.mappers.toGenre
import com.madrid.data.dataSource.local.mappers.toMovie
import com.madrid.data.dataSource.local.mappers.toSectionMovieTable
import com.madrid.data.dataSource.local.table.MovieSection
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.mapper.toCreateListStatus
import com.madrid.data.dataSource.mapper.toListOperationStatus
import com.madrid.data.dataSource.mapper.toMovieGenreTable
import com.madrid.data.dataSource.mapper.toMovieTable
import com.madrid.data.dataSource.remote.dto.list.MovieListBody
import com.madrid.data.dataSource.remote.dto.common.AddToFavoriteRequest
import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.mapper.toArtist
import com.madrid.data.dataSource.remote.mapper.toMovie
import com.madrid.data.dataSource.remote.mapper.toRatedMovie
import com.madrid.data.dataSource.remote.mapper.toReview
import com.madrid.data.dataSource.remote.mapper.toSimilarMovie
import com.madrid.data.dataSource.remote.mapper.toTrailer
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.SortType
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MovieRepository {
    private val pageNumberCached = 1
    private suspend fun MovieResult.toMovieWithGenres(): Movie {
        val genres = localDataSource
            .getMovieGenresByIds(this.genreIds ?: emptyList()).map { it.toGenre() }
        return this.toMovie(genres)
    }

    override suspend fun getMovieDetailsById(movieId: Int): Movie {
        return remoteDataSource.getMovieDetailsById(movieId).toMovie()
    }

    override suspend fun getMovieTrailersById(movieId: Int): List<Trailer> {
        return remoteDataSource.getMovieTrailersByMovieId(movieId).map { it.toTrailer() }
    }

    override suspend fun getMovieCreditsById(movieId: Int): List<Artist> {
        return remoteDataSource.getMovieCreditById(movieId).castNetwork?.map { it.toArtist() }
            ?: emptyList()
    }

    override suspend fun getMovieReviewsById(movieId: Int): List<Review> {
        return remoteDataSource.getMovieReviewsById(movieId).results?.map { it.toReview() }
            ?: emptyList()
    }

    override suspend fun getSimilarMoviesById(movieId: Int): List<Movie> {
        return remoteDataSource.getSimilarMoviesById(movieId).similarMovie?.map { it.toSimilarMovie() }
            ?: emptyList()
    }

    override suspend fun getRecommendedMovies(page: Int): List<Movie> {
        if (page == pageNumberCached) {
            val localMovies = localDataSource.getRecommendedMovies()
            if (localMovies.isNotEmpty()) {
                return localMovies.map { it.toMovie() }
            }
        }

        val remoteResult = remoteDataSource.getPopularMovies(page)
        val remoteMovies = remoteResult.movieResults.map { it.toMovie() }
        remoteMovies.forEach { movie ->
            localDataSource.insertSectionMovie(
                movie.toSectionMovieTable().copy(movieSection = MovieSection.RECOMMENDED.value)
            )
        }

        return remoteMovies
    }

    override suspend fun getExploreMoreMovies(page: Int): List<Movie> {
        return remoteDataSource.getTopRatedMovies(page).movieResults.map { it.toMovie() }
    }

    override suspend fun getTrendingMovies(page: Int): List<Movie> {
        if (page == pageNumberCached) {
            val localMovies = localDataSource.getTrendingMovies()
            if (localMovies.isNotEmpty()) {
                return localMovies.map {
                    val genres = localDataSource
                        .getMovieGenresByIds(listOf(it.genresIds)).map { genreTable -> genreTable.toGenre() }
                    it.toMovie(genres)
                }
            }
        }

        val remoteResult = remoteDataSource.getTrendingMovies(page)
        val remoteMovies = remoteResult.movieResults.map { it.toMovieWithGenres() }
        remoteMovies.forEach { movie ->
            localDataSource.insertSectionMovie(
                movie.toSectionMovieTable().copy(movieSection = MovieSection.TRENDING.value)
            )
        }

        return remoteMovies
    }

    override suspend fun getMoviesGenres(): List<Genre> {
        return localDataSource.getAllMovieGenres().ifEmpty {
            remoteDataSource.getMovieGenres().forEach {
                localDataSource.insertMovieGenre(it.toMovieGenreTable())
            }
            localDataSource.getAllMovieGenres()
        }.map { it.toGenre() }
    }

    override suspend fun increaseMovieGenreInterestPoints(genreTitle: String) {
        localDataSource.increaseMovieGenreInterestPoints(genreTitle)
    }

    override suspend fun getMoviesByGenres(): Map<String, List<Movie>> {
        val genresWithMovies = localDataSource.getMoviesByGenres()
        return genresWithMovies.associate { genreWithMovies ->
            val genreTitle = genreWithMovies.genre.genreTitle
            val movies = genreWithMovies.movies.map { it.toMovie() }
            genreTitle to movies
        }
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        if (page == pageNumberCached) {
            val localMovies = localDataSource.getTopRatingMovies()
            if (localMovies.isNotEmpty()) {
                return localMovies.map { it.toMovie() }
            }
        }

        val remoteResult = remoteDataSource.getTopRatedMovies(page)
        val remoteMovies = remoteResult.movieResults.map { it.toMovie() }
        remoteMovies.forEach { movie ->
            localDataSource.insertSectionMovie(
                movie.toSectionMovieTable().copy(movieSection = MovieSection.TOP_RATED.value)
            )
        }

        return remoteMovies
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val movies = remoteDataSource.getPopularMovies(
            page = page
        ).movieResults
        movies.map { movie ->
            localDataSource.insertMovie(movie.toMovieTable())
            movie.genreIds?.map { genreId ->
                localDataSource.relateMovieToGenre(
                    MovieGenreCrossRef(
                        movieId = movie.id ?: 0,
                        genreId = genreId
                    )
                )
            }
        }
        return movies.map { it.toMovie() }
    }

    override suspend fun getNowPlayingMovie(page: Int): List<Movie> {
        if (page == pageNumberCached) {
            val localMovies = localDataSource.getNowPlayingMovies()

            if (localMovies.isNotEmpty()) {
                return localMovies.map { it.toMovie() }
            }
        }

        val remoteResult = remoteDataSource.getNowPlayingMovie(page)

        val remoteMovies = remoteResult.nowPlayingMovieResults?.map { it.toMovie() } ?: emptyList()

        remoteMovies.forEach { movie ->
            localDataSource.insertSectionMovie(
                movie.toSectionMovieTable().copy(movieSection = MovieSection.NOW_PLAYING.value)
            )
        }
        return remoteMovies
    }

    override suspend fun getUpcomingMovie(page: Int): List<Movie> {
        if (page == pageNumberCached) {
            val localMovies = localDataSource.getUpComingMovies()
            if (localMovies.isNotEmpty()) {
                return localMovies.map { it.toMovie() }
            }
        }

        val remoteResult = remoteDataSource.getUpcomingMovie(page)
        val remoteMovies = remoteResult.upcomingMovieResult?.map { it.toMovie() } ?: emptyList()
        remoteMovies.forEach { movie ->
            localDataSource.insertSectionMovie(
                movie.toSectionMovieTable().copy(movieSection = MovieSection.UPCOMING.value)
            )
        }

        return remoteMovies
    }

    override suspend fun addRatingMovie(
        movieId: Int,
        rate: Double
    ) {
        return remoteDataSource.addRatingMovie(movieId, rate)
    }

    override suspend fun setMovieFavoriteStatus(
        movieId: Int,
        sessionId: String,
        isFavorite: Boolean
    ) {
        remoteDataSource.setMovieFavoriteStatus(
            movieId = movieId,
            sessionId = sessionId,
            isFavorite = isFavorite
        )
    }

    override suspend fun removeMovieFromList(listId: Int, mediaId: Int, sessionId: String) {
        remoteDataSource.removeMovieFromList(
            listId = listId,
            mediaId = mediaId,
            sessionId = sessionId
        )
    }

    override suspend fun getMoviesByGenreId(
        page: Int,
        genreId: Int?,
        sortBy: SortType
    ): List<Movie> {
        val sortType = getSortType(sortBy)
        return remoteDataSource.getMoviesByGenreId(
            page,
            genreId,
            sortType
        ).movieResults.map { it.toMovie() }
    }

    override suspend fun getUserMovieRate(sessionId: String): List<GetUserRatedMovieUseCase.RatedMovie> {
        val result = remoteDataSource.getUserRatingForMovie(
            sessionId = sessionId
        )
        return result.ratedMovie.map { it.toRatedMovie() }
    }

    override suspend fun clearHomeMoviesCache() {
        localDataSource.clearHomeMoviesCache()
    }

    override suspend fun clearMovieGenres(){
        localDataSource.clearMovieGenres()
    }

    override suspend fun addMovieToHistory(movieId: Int) {
        localDataSource.addMovieToHistory(movieId = movieId)
    }

    override suspend fun deleteMovieFromHistory(movieId: Int) {
        localDataSource.deleteMovieFromHistory(movieId)
    }

    override suspend fun getAllMoviesInHistory(): List<Movie> {
        val moviesIds = localDataSource.getAllMoviesInHistory().map { it.mediaId }
        return moviesIds.map { getMovieDetailsById(it) }
    }

    override suspend fun getFavoriteMovies(sessionId: String): List<Movie> {
        return remoteDataSource.getFavoriteMovies(sessionId).map { movie ->
            movie.toMovieWithGenres()
        }
    }

    override suspend fun createMovieList(
        sessionId: String,
        name: String,
        description: String,
        language: String
    ): ListOperationStatus {
        val body = MovieListBody(name, description, language)
        val response = remoteDataSource.createMovieList(sessionId, body)
        return response.toCreateListStatus()
    }

    override suspend fun addMovieToList(
        listId: Int,
        sessionId: String,
        mediaId: Int
    ): ListOperationStatus {
        val response = remoteDataSource.addMovieToList(
            listId = listId,
            sessionId = sessionId,
            movieId = mediaId
        )
        return response.toListOperationStatus()
    }


}