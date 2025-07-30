package com.madrid.data.dataSource.remote.response.movie

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingMovieResponse(
    @SerializedName("dates")
    val dates: DatesForNowPlaying?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val nowPlayingMovieResults: List<NowPlayingMovieResult>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

@Serializable
data class DatesForNowPlaying(
    @SerializedName("maximum")
    val maximum: String?,
    @SerializedName("minimum")
    val minimum: String?
)

@Serializable
data class NowPlayingMovieResult(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)