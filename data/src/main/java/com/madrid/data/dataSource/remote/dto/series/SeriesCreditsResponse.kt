package com.madrid.data.dataSource.remote.dto.series

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class SeriesCreditResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val seriesCastNetwork: List<SeriesCastNetwork>?,
)

@Serializable
data class SeriesCastNetwork(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("order")
    val order: String?,
)