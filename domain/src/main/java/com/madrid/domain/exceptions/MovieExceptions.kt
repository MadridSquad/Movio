package com.madrid.domain.exceptions

open class MovioException(message: String) : Exception(message)


class UnknownException(message: String) : MovioException(message)

open class AuthorizationException(message: String) : MovioException(message)
class InvalidCredentialsException : AuthorizationException("Invalid username or password")
class AccountLockedException : AuthorizationException("Account locked. Contact support.")
class SessionExpiredException : AuthorizationException("Session expired. Please login again.")
class GuestLoginException : AuthorizationException("Guest login failed")
class UnauthorizedException : AuthorizationException("Unauthorized")

// Validation Exceptions
sealed class ValidationException(message: String) : AuthorizationException(message) {

    sealed class EmptyField(fieldName: String) :
        ValidationException("$fieldName cannot be empty") {
        object Username : EmptyField("Username")
        object Password : EmptyField("Password")
    }

    companion object {
        fun validateField(fieldName: String, value: String) {
            if (value.isBlank()) {
                throw when (fieldName) {
                    "Username" -> EmptyField.Username
                    "Password" -> EmptyField.Password
                    else -> throw IllegalArgumentException("Invalid field name: $fieldName")

                }
            }
        }
    }
}

open class ServerException(message: String) : MovioException(message)

class TimeoutException(message: String) : ServerException(message = "Timeout Error: $message")

class NetworkException(message: String) : MovioException(message)

class NotFoundException(message: String) : MovioException(message = "Not Found: $message")