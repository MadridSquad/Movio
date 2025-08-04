package com.madrid.data.dataSource.remote.dto.authentication

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDetailsResponse(
    val avatar: AvatarDto,
    val id: Int,
    @SerializedName("iso_639_1")
    val language: String?,
    @SerializedName("iso_3166_1")
    val country: String?,
    val name: String?,
    @SerializedName("include_adult")
    val isAdult: Boolean?,
    val username: String?
)

@Serializable
data class AvatarDto(
    val gravatar: GravatarDto,
    val tmdb: TmdbDto
)

@Serializable
data class GravatarDto(
    val hash: String
)

@Serializable
data class TmdbDto(
    @SerializedName("avatar_path")
    val avatarPath: String?
)