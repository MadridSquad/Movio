package com.madrid.data.dataSource.remote.dto.common

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerResponse(
    val id: Int,
    val results: List<TrailerResult>
)

@Serializable
data class TrailerResult(
    @SerializedName("key")
    val key: String,
    @SerializedName("id")
    val id: String
)
