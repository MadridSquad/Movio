package com.madrid.data.dataSource.remote.utils

import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.InvalidValidationPasswordException
import com.madrid.domain.exceptions.InvalidValidationUserException
import com.madrid.domain.exceptions.MovioException
import com.madrid.domain.exceptions.NetworkException
import com.madrid.domain.exceptions.NotFoundException
import com.madrid.domain.exceptions.ServerException
import com.madrid.domain.exceptions.TimeoutException
import com.madrid.domain.exceptions.UnauthorizedException
import com.madrid.domain.exceptions.UnknownException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException



suspend fun <T> retrofitResponseWrapper(apiCall: suspend () -> T): T {
    try {
        return apiCall()
    } catch (t: Throwable) {
        throw mapRetrofitError(t)
    }
}

private fun mapRetrofitError(t: Throwable): MovioException {
    return when (t) {
        is HttpException -> {
            val code = t.code()
            val errorBody = try {
                t.response()?.errorBody()?.string() ?: ""
            } catch (e: Exception) {
                ""
            }

            when (code) {
                400 -> handleBadRequest(errorBody)
                401 -> handleAuthenticationError(errorBody)
                403 -> UnauthorizedException("auth")
                404 -> NotFoundException("resource.not_found")
                408 -> TimeoutException("network.timeout")
                in 500..599 -> ServerException("server.error")
                else -> UnknownException("unknown.error")
            }
        }
        is SocketTimeoutException -> TimeoutException("network.timeout")
        is IOException -> NetworkException("network.error")
        else -> UnknownException("unknown.error")
    }
}

private fun handleBadRequest(errorBody: String): MovioException {
    return when {
        errorBody.isEmpty() -> ServerException("server.bad_request")
        errorBody.contains("empty_fields", true) ->
            InvalidValidationUserException("validation.empty_fields")
        errorBody.contains("invalid_username", true) ->
            InvalidValidationUserException("validation.invalid_username")
        errorBody.contains("invalid_password", true) ->
            InvalidValidationPasswordException("validation.invalid_password")
        errorBody.contains("invalid_credentials", true) ->
            InvalidCredentialsException("auth.invalid_credentials")
        else -> ServerException("server.bad_request")
    }
}

private fun handleAuthenticationError(errorBody: String): MovioException {
    return when {
        errorBody.isBlank() -> UnauthorizedException("auth.unauthorized")

        errorBody.contains("invalid_username", true) ->
            InvalidValidationUserException("validation.invalid_username")

        errorBody.contains("invalid_password", true) ->
            InvalidValidationPasswordException("validation.invalid_password")

        errorBody.contains("invalid_credentials", true) ||
                errorBody.contains("username and/or password", true) ->
            InvalidCredentialsException("auth.invalid_credentials")

        errorBody.contains("token_expired", true) ->
            UnauthorizedException("auth.token_expired")

        else -> UnauthorizedException("auth.unauthorized")
    }
}