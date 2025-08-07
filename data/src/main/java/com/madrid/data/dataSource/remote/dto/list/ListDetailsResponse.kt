package com.madrid.data.dataSource.remote.dto.list

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ListsDetailsResponse(
    @SerializedName("created_by")
    val createdBy: String,
    val description: String,
    @SerializedName("favorite_count")
    val favoriteCount: Int = 0,
    val items: List<ListItemDto> = emptyList(),
    @SerializedName("item_count")
    val itemCount: Int = 0,
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
)


@Serializable
data class ListItemDto(
    val adult: Boolean = true,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    val id: Int = 0,
    @SerializedName("media_type")
    val mediaType: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean = true,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0
)