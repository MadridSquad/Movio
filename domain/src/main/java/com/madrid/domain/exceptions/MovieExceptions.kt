package com.madrid.domain.exceptions

open class MovioException(message: String) : Exception(message)


class UnknownException(message: String) : MovioException(message)

open class AuthorizationException(message: String) : MovioException(message)
class UnauthorizedException : AuthorizationException(message = "Unauthorized")
class ValidationException : AuthorizationException(message = "Validation")

open class ServerException(message: String) : MovioException(message)

class TimeoutException(message: String) : ServerException(message = "Timeout Error: $message")

class NetworkException(message: String) : MovioException(message)

class NotFoundException(message: String) : MovioException(message = "Not Found: $message")