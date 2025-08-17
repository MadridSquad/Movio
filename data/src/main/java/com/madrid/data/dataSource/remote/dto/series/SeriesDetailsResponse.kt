package com.madrid.data.dataSource.remote.dto.series

import com.google.gson.annotations.SerializedName
import com.madrid.data.dataSource.remote.dto.genre.RemoteGenreDto
import kotlinx.serialization.Serializable


@Serializable
data class SeriesDetailsResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val director: List<Director>?,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genres")
    val genres: List<RemoteGenreDto>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("in_production")
    val inProduction: Boolean?,
    @SerializedName("languages")
    val languages: List<String>?,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: FinaleEpisode?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("networks")
    val channels: List<Channels>?,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanies>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountries>?,
    @SerializedName("seasons")
    val seasons: List<SeasonsNetwork>?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<Translation>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
)

@Serializable
data class Director(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("profile_path")
    val profilePath: String?,
)

@Serializable
data class SeriesGenres(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)

@Serializable
data class FinaleEpisode(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_number")
    val episodeNumber: Int?,
    @SerializedName("production_code")
    val productionCode: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    @SerializedName("show_id")
    val showId: Int?,
    @SerializedName("still_path")
    val stillPath: String?,
)

@Serializable
data class Channels(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?,
)

@Serializable
data class ProductionCompanies(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?,
)

@Serializable
data class ProductionCountries(
    @SerializedName("iso_3166_1")
    val iso: String?,
    @SerializedName("name")
    val name: String?,
)

@Serializable
data class SeasonsNetwork(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
)

@Serializable
data class Translation(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_639_1")
    val iso: String?,
    @SerializedName("name")
    val name: String?,
)