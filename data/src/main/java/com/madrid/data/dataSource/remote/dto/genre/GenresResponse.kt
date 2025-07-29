package com.madrid.data.dataSource.remote.dto.genre

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerializedName("genres")
    val genres: List<MovieGenre>?,
)
@Serializable
data class MovieGenre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)
