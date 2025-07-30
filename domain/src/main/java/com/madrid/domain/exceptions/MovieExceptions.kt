package com.madrid.domain.exceptions

open class MovioException(message: String) : Exception(message)


class UnknownException(message: String) : MovioException(message)

open class AuthorizationException(message: String) : MovioException(message)
class InvalidCredentialsException : AuthorizationException("Invalid username or password")
class AccountLockedException : AuthorizationException("Account locked. Contact support.")
class SessionExpiredException : AuthorizationException("Session expired. Please login again.")
class GuestLoginException : AuthorizationException("Guest login failed")
class UnauthorizedException : AuthorizationException("Unauthorized")

// Validation Exceptions (مضافة حديثًا)
class EmptyUsernameException : AuthorizationException("Username cannot be empty")
class UsernameTooShortException : AuthorizationException("Username must be at least 3 characters")
class EmptyPasswordException : AuthorizationException("Password cannot be empty")
class WeakPasswordException : AuthorizationException("Password must be at least 6 characters")


open class ServerException(message: String) : MovioException(message)

class TimeoutException(message: String) : ServerException(message = "Timeout Error: $message")

class NetworkException(message: String) : MovioException(message)

class NotFoundException(message: String) : MovioException(message = "Not Found: $message")