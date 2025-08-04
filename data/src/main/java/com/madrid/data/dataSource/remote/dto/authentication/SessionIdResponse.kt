package com.madrid.data.dataSource.remote.dto.authentication

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SessionIdResponse(
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String,
)