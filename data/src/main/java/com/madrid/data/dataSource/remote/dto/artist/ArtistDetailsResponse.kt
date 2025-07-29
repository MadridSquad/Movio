package com.madrid.data.dataSource.remote.dto.artist

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDetailsResponse(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("also_known_as")
    val nickName: List<String>? = null,
    @SerializedName("biography")
    val biography: String? = null,
    @SerializedName("birthday")
    val birthDay: String? = null,
    @SerializedName("deathday")
    val deathDay: String? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("homepage")
    val homePage: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("known_for_department")
    val role: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)