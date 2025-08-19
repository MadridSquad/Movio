@file:Suppress("UnusedFlow")

package com.madrid.domain.usecase.authentication

import com.google.common.truth.Truth.assertThat
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

class CheckFirstLaunchUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private lateinit var useCase: CheckFirstLaunchUseCase

    @Before
    fun setUp() {
        useCase = CheckFirstLaunchUseCase(authenticationRepository)
    }

    @Test
    fun `isFirstLaunch should return true when repository returns true`() = runTest {
        every { authenticationRepository.isFirstLaunch() } returns flowOf(true)

        val result = useCase.isFirstLaunch().first()

        assertThat(result).isTrue()
        verify(exactly = 1) { authenticationRepository.isFirstLaunch() }
    }

    @Test
    fun `isFirstLaunch should return false when repository returns false`() = runTest {
        every { authenticationRepository.isFirstLaunch() } returns flowOf(false)

        val result = useCase.isFirstLaunch().first()

        assertThat(result).isFalse()
        verify(exactly = 1) { authenticationRepository.isFirstLaunch() }
    }

    @Test
    fun `isFirstLaunch should return flow from repository`() = runTest {
        val expectedFlow = flowOf(true)
        every { authenticationRepository.isFirstLaunch() } returns expectedFlow

        val result = useCase.isFirstLaunch()

        assertThat(result).isEqualTo(expectedFlow)
        verify(exactly = 1) { authenticationRepository.isFirstLaunch() }
    }

    @Test
    fun `setOnBoardingDone should call repository with true parameter`() = runTest {
        useCase.setOnBoardingDone(isCompleted = true)

        coVerify(exactly = 1) { authenticationRepository.setOnboardingCompleted(isCompleted = true) }
    }

    @Test
    fun `setOnBoardingDone should call repository with false parameter`() = runTest {
        useCase.setOnBoardingDone(isCompleted = false)

        coVerify(exactly = 1) { authenticationRepository.setOnboardingCompleted(isCompleted = false) }
    }

    @Test(expected = RuntimeException::class)
    fun `isFirstLaunch should throw exception when repository fails`() = runTest {
        every { authenticationRepository.isFirstLaunch() } throws RuntimeException("Repository error")

        useCase.isFirstLaunch()
    }

    @Test(expected = RuntimeException::class)
    fun `setOnBoardingDone should throw exception when repository fails`() = runTest {
        coEvery { authenticationRepository.setOnboardingCompleted(any()) } throws RuntimeException("Repository error")

        useCase.setOnBoardingDone(isCompleted = true)
    }
}