package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.local.mappers.toGenre
import com.madrid.data.dataSource.remote.dto.artist.KnownForMoviesNetwork
import com.madrid.data.dataSource.remote.dto.common.TrailerResult
import com.madrid.data.dataSource.remote.dto.movie.CastNetwork
import com.madrid.data.dataSource.remote.dto.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.movie.MovieReviewResult
import com.madrid.data.dataSource.remote.dto.movie.SimilarMovieNetwork
import com.madrid.data.dataSource.remote.dto.movie.NowPlayingMovieResponse
import com.madrid.data.dataSource.remote.dto.movie.NowPlayingMovieResult
import com.madrid.data.dataSource.remote.dto.movie.UpcomingMovieResult
import com.madrid.data.dataSource.remote.dto.movie.UpcomingMoviesResponse
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Trailer

fun MovieDetailsResponse.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        movieDuration = this.runtime?.toString() ?: "",
        description = this.overview ?: "",
        genre = this.remoteGenreDtos.map { it.toGenre()},
    )
}

fun MovieResult.toMovie(
    genres : List<Genre> = emptyList()
): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        movieDuration = "",
        description = this.overview ?: "",
        genre = genres,
    )
}

fun KnownForMoviesNetwork.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        movieDuration = "",
        description = this.overview ?: "",
        genre = emptyList()
    )
}

fun CastNetwork.toArtist(): Artist {
    return Artist(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
        role = this.knownForDepartment ?: "",
        dateOfBirth = "",
        country = "",
        overview = "",
    )
}

fun MovieReviewResult.toReview(): Review {
    return Review(
        reviewerName = this.author ?: "",
        rate = this.authorDetails?.rating ?: 0.0,
        date = this.createdAt ?: "",
        comment = this.content ?: "",
        reviewId = this.id ?: "",
        reviewerPhotoUrl = "https://image.tmdb.org/t/p/original${this.authorDetails?.avatarPath}",
    )
}

fun SimilarMovieNetwork.toSimilarMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        movieDuration = "",
        description = this.overview ?: "",
        genre = emptyList(),
    )
}


fun TrailerResult.toTrailer(): Trailer {
    return Trailer(
        key = this.key,
        id = this.id
    )
}

fun NowPlayingMovieResponse.toMovies(): List<Movie> {
    return this.nowPlayingMovieResults?.map {
        it.toMovie()
    } ?: emptyList()
}

fun NowPlayingMovieResult.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        description = this.overview ?: "",
        genre = this.genreIds?.map { Genre(id = it, name = "") } ?: emptyList(),
        movieDuration = "",
    )
}

fun UpcomingMoviesResponse.toMovies(): List<Movie> {
    return this.upcomingMovieResult?.map {
        it.toMovie()
    } ?: emptyList()
}

fun UpcomingMovieResult.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        description = this.overview ?: "",
        genre = this.genreIds?.map { Genre(id = it, name = "") } ?: emptyList(),
        movieDuration = "",
    )
}