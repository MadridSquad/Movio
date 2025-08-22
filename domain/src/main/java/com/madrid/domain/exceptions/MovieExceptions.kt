package com.madrid.domain.exceptions

open class MovioException(message: String) : Exception(message)

class UnknownException(message: String) : MovioException(message)

open class AuthorizationException(message: String) : MovioException(message)
class InvalidCredentialsException(message: String) : AuthorizationException(message)
class UnauthorizedException(message: String) : AuthorizationException(message)

open  class ValidationException(message: String) : MovioException(message)
class InvalidValidationPasswordException(message: String) : ValidationException(message)
class InvalidValidationUserException(message: String) : ValidationException(message)

open class ServerException(message: String) : MovioException(message)
class TimeoutException(message: String) : ServerException(message = "Timeout Error: $message")

class NetworkException(message: String) : MovioException(message)

class NotFoundException(message: String) : MovioException(message = "Not Found: $message")
class AlreadyExistsException(message: String) : MovioException(message = "Already Exists: $message")