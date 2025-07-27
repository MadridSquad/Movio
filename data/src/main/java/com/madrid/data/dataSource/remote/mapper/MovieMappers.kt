package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.response.common.TrailerResponse
import com.madrid.data.dataSource.remote.response.genre.MovieGenre
import com.madrid.data.dataSource.remote.response.movie.CastNetwork
import com.madrid.data.dataSource.remote.response.movie.MovieCreditsResponse
import com.madrid.data.dataSource.remote.response.movie.MovieDetailsResponse
import com.madrid.data.dataSource.remote.response.movie.MovieResult
import com.madrid.data.dataSource.remote.response.movie.MovieReviewResponse
import com.madrid.data.dataSource.remote.response.movie.MovieReviewResult
import com.madrid.data.dataSource.remote.response.movie.SearchMovieResponse
import com.madrid.data.dataSource.remote.response.movie.SimilarMovieNetwork
import com.madrid.data.dataSource.remote.response.movie.SimilarMoviesResponse
import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.ReviewResult
import com.madrid.domain.entity.SearchResult
import com.madrid.domain.entity.SimilarMovie
import com.madrid.domain.entity.Trailer

// region Search
fun SearchMovieResponse.toSearchResult(): SearchResult {
    return SearchResult(
        page = this.page,
        searchResults = this.movieResults?.map { it.toMovie() },
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}
// endregion

// region Details
fun MovieDetailsResponse.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        movieDuration = this.runtime?.toString() ?: "",
        description = this.overview ?: "",
        genre = this.movieGenres?.map { it.toMediaGenre().title } ?: emptyList(),
        profilePage = this.homepage ?: ""
    )
}

fun MovieResult.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0,
        yearOfRelease = this.releaseDate ?: "",
        movieDuration = "",
        description = this.overview ?: "",
        genre = listOf(),

        )
}
// endregion

// region Cast
fun MovieCreditsResponse.toCredits(): Credits {
    return Credits(
        id = this.id ?: 0,
        cast = this.castNetwork?.map { it.toCast() },
    )
}

fun CastNetwork.toCast(): Cast {
    return Cast(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
    )
}
// endregion

// region Review
fun MovieReviewResponse.toReviewResult(
): ReviewResult {
    return ReviewResult(
        mediaId = this.id ?: 0,
        page = this.page ?: 0,
        results = this.results?.map { it.toReview() } ?: emptyList(),
        totalPages = this.totalPages ?: 0,
        totalResults = this.totalResults ?: 0
    )
}

fun MovieReviewResult.toReview(): Review {
    return Review(
        userId = this.id ?: "0",
        rate = this.authorDetails?.rating ?: 0.0,
        dateOfRelease = this.createdAt ?: "",
        comment = this.content ?: ""
    )
}
// endregion

// region Similar Movie
fun SimilarMovieNetwork.toSimilarMovie(): SimilarMovie {
    return SimilarMovie(
        id = this.id ?: 0,
        title = this.title ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        rate = this.voteAverage ?: 0.0
    )
}

fun SimilarMoviesResponse.toSimilarMovies(): SimilarMedia {
    return SimilarMedia(
        page = this.page,
        results = this.similarMovie?.map { it.toSimilarMovie() },
        totalPages = this.totalPages,
        totalResults = this.totalResults

    )
}
// endregion

//region Genre
fun MovieGenre.toMediaGenre(): MediaGenre {
    return MediaGenre(
        id = this.id ?: 0,
        title = this.name ?: ""
    )
}
// endregion

// region Trailer

fun TrailerResponse.toTrailer(): Trailer {
    return Trailer(
        key = this.results.firstOrNull()?.key ?: "",
        id = this.results.firstOrNull()?.id ?: ""
    )
}
// endregion