package com.madrid.data.dataSource.encrypted

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.encodeToString
import java.util.Base64

@Serializable
data class UserData(
    val token: String? = null
)


object UserDataSerializer : Serializer<UserData> {
    override suspend fun readFrom(input: InputStream): UserData {
        val encryptedBytes = withContext(Dispatchers.IO) {
            input.use { it.readBytes() }
        }
        val base64 = Base64.getDecoder().decode(encryptedBytes)
        val decryptedBytes = Crypto.decrypt(Base64.getDecoder().decode(encryptedBytes))
        val json = decryptedBytes.decodeToString()
        return Json.decodeFromString(json)
    }

    override suspend fun writeTo(
        t: UserData,
        output: OutputStream
    ) {
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encryptedBytes = Crypto.encrypt(bytes)
        val base64 = Base64.getEncoder().encode(encryptedBytes)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(base64)
            }
        }
    }

    override val defaultValue: UserData
        get() = UserData()
}