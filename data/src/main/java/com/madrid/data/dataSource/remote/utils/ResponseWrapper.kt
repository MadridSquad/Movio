package com.madrid.data.dataSource.remote.utils



import com.madrid.domain.exceptions.UnauthorizedException
import com.madrid.domain.exceptions.UnknownException
import com.madrid.domain.exceptions.ServerException
import com.madrid.domain.exceptions.TimeoutException
import okhttp3.Response


fun responseWrapper(
    response: Response,
): Response {
    val redirectRange = 300..399
    val invalidRequestCode = 400
    val unauthorizedRange = 401..403
    val timeoutCode = 408
    val serverErrorRange = 500..599

    try {
        return response
    } catch (e: Exception) {
        when (response.code) {
            in redirectRange -> throw UnknownException(message = "Redirect Error: ${e.message}")
            in unauthorizedRange -> throw UnauthorizedException(" Unauthorized Error: ${e.message}")
            timeoutCode -> throw TimeoutException(message = "Timeout Error: ${e.message}")
            in serverErrorRange -> throw ServerException(message = "Server Error: ${e.message}")
            invalidRequestCode -> throw ServerException(message = "Invalid Request: ${e.message}")

            else -> throw UnknownException(e.message.toString())
        }
    }
}
