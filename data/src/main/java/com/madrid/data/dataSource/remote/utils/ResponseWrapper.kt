package com.madrid.data.dataSource.remote.utils

import com.madrid.domain.exceptions.AlreadyExistsException
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.NotFoundException
import com.madrid.domain.exceptions.ServerException
import com.madrid.domain.exceptions.UnauthorizedException
import com.madrid.domain.exceptions.UnknownException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T> responseWrapper(
    block: suspend () -> T
): T {

    val unauthorizedCode = 401
    val notFoundCode = 404
    val timeoutCode = 408
    val serverErrorRange = 500..504

    return try {
        val result = block()
        result
    } catch (e: HttpException) {
        when (e.code()) {
            unauthorizedCode -> throw UnauthorizedException("Unauthorized Exception: ${e.message()}")
            notFoundCode -> throw NotFoundException("Not Found Exception: ${e.message()}")
            timeoutCode -> throw ServerException("Time out Exception: ${e.message()}")
            in serverErrorRange -> throw ServerException("Server Exception: ${e.message()}")
            else -> {
                val responseBody = e.response()?.errorBody()?.string()
                val errorResponseBody =
                    responseBody?.let { Json.decodeFromString<ErrorResponseBody>(it) }
                throw mapHttpCodeToDomainException(
                    errorResponseBody?.status_code,
                    errorResponseBody?.status_message
                )
            }
        }
    } catch (e: ConnectException) {
        throw NetworkException("No Internet Connection: ${e.message}")
    } catch (e: SerializationException) {
        throw UnknownException("Serialization Error: ${e.message}")
    } catch (e: UnknownHostException) {
        throw NetworkException("No Internet Connection: ${e.message}")
    } catch (e: SocketTimeoutException) {
        throw NetworkException("Timeout Error: ${e.message}")
    } catch (e: Exception) {
        throw UnknownException("Unknown Exception: ${e.stackTrace}")
    }
}

fun mapHttpCodeToDomainException(
    code: Int?,
    message: String? = null
): MovioException {
    return when (code) {
        8 -> { // 403 duplicate entry
            AlreadyExistsException("Already Exists: $code - $message")
        }

        26, 45 -> {
            UnauthorizedException("Authorization Exception: $code - $message")
        }

        else -> {
            UnknownException("Unknown error. Check TMDB Errors with code $code \n$message")
        }
    }
}

@Serializable
data class ErrorResponseBody(
    val status_code: Int,
    val status_message: String?,
    val success: Boolean? = null,
)