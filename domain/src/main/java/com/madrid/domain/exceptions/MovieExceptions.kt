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
sealed class ValidationException(fieldName: String, message: String) : AuthorizationException(message) {
    sealed class EmptyField(fieldName: String) :
        ValidationException(fieldName, "$fieldName cannot be empty") {
        object Username : EmptyField("Username")
        object Password : EmptyField("Password")
    }

    sealed class MinimumLength(
        fieldName: String,
        minLength: Int
    ) : ValidationException(fieldName, "$fieldName must be at least $minLength characters") {
        class Username(minLength: Int = 3) : MinimumLength("Username", minLength)
        class Password(minLength: Int = 6) : MinimumLength("Password", minLength)
    }

    companion object {
        fun validateField(
            fieldName: String,
            value: String,
            minLength: Int? = null
        ) {
            when {
                value.isBlank() -> throw when (fieldName) {
                    "Username" -> EmptyField.Username
                    "Password" -> EmptyField.Password
                    else -> IllegalArgumentException("Invalid field name: $fieldName")
                }
                minLength != null && value.length < minLength -> throw when (fieldName) {
                    "Username" -> MinimumLength.Username(minLength)
                    "Password" -> MinimumLength.Password(minLength)
                    else -> IllegalArgumentException("Invalid field name: $fieldName")
                }
            }
        }
    }
}


open class ServerException(message: String) : MovioException(message)

class TimeoutException(message: String) : ServerException(message = "Timeout Error: $message")

class NetworkException(message: String) : MovioException(message)

class NotFoundException(message: String) : MovioException(message = "Not Found: $message")