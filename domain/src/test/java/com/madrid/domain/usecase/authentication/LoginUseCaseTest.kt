package com.madrid.domain.usecase.authentication

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.UnknownException
import com.madrid.domain.exceptions.ValidationException
import com.madrid.domain.repository.AuthenticationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class LoginUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        useCase = LoginUseCase(authenticationRepository)
    }

    @Test
    fun `login should return true when login is successful`() = runTest {
        coEvery { authenticationRepository.login("testuser", "testpass") } returns true

        val result = useCase.login("testuser", "testpass")

        assertThat(result).isTrue()
        coVerify(exactly = 1) { authenticationRepository.login("testuser", "testpass") }
    }

    @Test(expected = InvalidCredentialsException::class)
    fun `login should throw InvalidCredentialsException when repository returns false`() =
        runTest {
            coEvery { authenticationRepository.login("testuser", "testpass") } returns false

            useCase.login("testuser", "testpass")
        }

    @Test(expected = ValidationException::class)
    fun `login should throw ValidationException when username is empty`() = runTest {
        useCase.login("", "testpass")
    }

    @Test(expected = ValidationException::class)
    fun `login should throw ValidationException when password is empty`() = runTest {
        useCase.login("testuser", "")
    }

    @Test(expected = ValidationException::class)
    fun `login should throw ValidationException when both credentials are empty`() = runTest {
        useCase.login("", "")
    }

    @Test(expected = UnknownException::class)
    fun `login should throw UnknownException when repository throws unexpected exception`() =
        runTest {
            coEvery {
                authenticationRepository.login(
                    "testuser",
                    "testpass"
                )
            } throws RuntimeException("Network error")

            useCase.login("testuser", "testpass")
        }

    @Test
    fun `loginAsGuest should return true when guest login is successful`() = runTest {
        coEvery { authenticationRepository.loginAsGuest() } returns true

        val result = useCase.loginAsGuest()

        assertThat(result).isTrue()
        coVerify(exactly = 1) { authenticationRepository.loginAsGuest() }
    }

    @Test
    fun `loginAsGuest should throw GuestLoginException when repository returns false`() = runTest {
        coEvery { authenticationRepository.loginAsGuest() } returns false

        useCase.loginAsGuest()
    }

    @Test(expected = UnknownException::class)
    fun `loginAsGuest should throw UnknownException when repository throws unexpected exception`() =
        runTest {
            coEvery { authenticationRepository.loginAsGuest() } throws RuntimeException("Network error")

            useCase.loginAsGuest()
        }

    @Test
    fun `checkActiveSession should return true when user is logged in`() = runTest {
        every { authenticationRepository.isUserLoggedIn() } returns flowOf(true)

        val result = useCase.checkActiveSession().first()

        assertThat(result).isTrue()
        verify(exactly = 1) { authenticationRepository.isUserLoggedIn() }
    }

    @Test
    fun `checkActiveSession should return false when user is not logged in`() = runTest {
        every { authenticationRepository.isUserLoggedIn() } returns flowOf(false)

        val result = useCase.checkActiveSession().first()

        assertThat(result).isFalse()
        verify(exactly = 1) { authenticationRepository.isUserLoggedIn() }
    }

    @Test
    fun `isGuest should return true when user is guest`() = runTest {
        every { authenticationRepository.isGuest() } returns flowOf(true)

        val result = useCase.isGuest().first()

        assertThat(result).isTrue()
        verify(exactly = 1) { authenticationRepository.isGuest() }
    }

    @Test
    fun `isGuest should return false when user is not guest`() = runTest {
        every { authenticationRepository.isGuest() } returns flowOf(false)

        val result = useCase.isGuest().first()

        assertThat(result).isFalse()
        verify(exactly = 1) { authenticationRepository.isGuest() }
    }

    @Test
    fun `login should call validateCredentials before repository login`() = runTest {
        coEvery { authenticationRepository.login("testuser", "testpass") } returns true

        useCase.login("testuser", "testpass")

        coVerify(exactly = 1) { authenticationRepository.login("testuser", "testpass") }
    }
}