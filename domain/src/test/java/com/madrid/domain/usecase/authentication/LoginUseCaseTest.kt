package com.madrid.domain.usecase.authentication

import com.google.common.truth.Truth
import com.madrid.domain.exceptions.GuestLoginException
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
    fun `execute SHOULD return true when login is successful`() = runTest {
        coEvery { authenticationRepository.login("testuser", "testpass") } returns true

        val result = useCase.execute("testuser", "testpass")

        Truth.assertThat(result).isTrue()
        coVerify(exactly = 1) { authenticationRepository.login("testuser", "testpass") }
    }

    @Test(expected = InvalidCredentialsException::class)
    fun `execute SHOULD throw InvalidCredentialsException when repository returns false`() =
        runTest {
            coEvery { authenticationRepository.login("testuser", "testpass") } returns false

            useCase.execute("testuser", "testpass")
        }

    @Test(expected = ValidationException::class)
    fun `execute SHOULD throw ValidationException when username is empty`() = runTest {
        useCase.execute("", "testpass")
    }

    @Test(expected = ValidationException::class)
    fun `execute SHOULD throw ValidationException when password is empty`() = runTest {
        useCase.execute("testuser", "")
    }

    @Test(expected = ValidationException::class)
    fun `execute SHOULD throw ValidationException when both credentials are empty`() = runTest {
        useCase.execute("", "")
    }

    @Test(expected = UnknownException::class)
    fun `execute SHOULD throw UnknownException when repository throws unexpected exception`() =
        runTest {
            coEvery {
                authenticationRepository.login(
                    "testuser",
                    "testpass"
                )
            } throws RuntimeException("Network error")

            useCase.execute("testuser", "testpass")
        }

    @Test
    fun `loginAsGuest SHOULD return true when guest login is successful`() = runTest {
        coEvery { authenticationRepository.loginAsGuest() } returns true

        val result = useCase.loginAsGuest()

        Truth.assertThat(result).isTrue()
        coVerify(exactly = 1) { authenticationRepository.loginAsGuest() }
    }

    @Test(expected = GuestLoginException::class)
    fun `loginAsGuest SHOULD throw GuestLoginException when repository returns false`() = runTest {
        coEvery { authenticationRepository.loginAsGuest() } returns false

        useCase.loginAsGuest()
    }

    @Test(expected = UnknownException::class)
    fun `loginAsGuest SHOULD throw UnknownException when repository throws unexpected exception`() =
        runTest {
            coEvery { authenticationRepository.loginAsGuest() } throws RuntimeException("Network error")

            useCase.loginAsGuest()
        }

    @Test
    fun `checkActiveSession SHOULD return true when user is logged in`() = runTest {
        every { authenticationRepository.isUserLoggedIn() } returns flowOf(true)

        val result = useCase.checkActiveSession().first()

        Truth.assertThat(result).isTrue()
        verify(exactly = 1) { authenticationRepository.isUserLoggedIn() }
    }

    @Test
    fun `checkActiveSession SHOULD return false when user is not logged in`() = runTest {
        every { authenticationRepository.isUserLoggedIn() } returns flowOf(false)

        val result = useCase.checkActiveSession().first()

        Truth.assertThat(result).isFalse()
        verify(exactly = 1) { authenticationRepository.isUserLoggedIn() }
    }

    @Test
    fun `isGuest SHOULD return true when user is guest`() = runTest {
        every { authenticationRepository.isGuest() } returns flowOf(true)

        val result = useCase.isGuest().first()

        Truth.assertThat(result).isTrue()
        verify(exactly = 1) { authenticationRepository.isGuest() }
    }

    @Test
    fun `isGuest SHOULD return false when user is not guest`() = runTest {
        every { authenticationRepository.isGuest() } returns flowOf(false)

        val result = useCase.isGuest().first()

        Truth.assertThat(result).isFalse()
        verify(exactly = 1) { authenticationRepository.isGuest() }
    }

    @Test
    fun `execute SHOULD call validateCredentials before repository login`() = runTest {
        coEvery { authenticationRepository.login("testuser", "testpass") } returns true

        useCase.execute("testuser", "testpass")

        coVerify(exactly = 1) { authenticationRepository.login("testuser", "testpass") }
    }
}