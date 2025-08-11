package com.madrid.data.datasource.remote.dto.rating

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RateRequest(
    @SerializedName("value")
    val value: Double
)