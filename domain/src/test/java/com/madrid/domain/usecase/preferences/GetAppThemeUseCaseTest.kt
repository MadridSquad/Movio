package com.madrid.domain.usecase.preferences

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppTheme
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class GetAppThemeUseCaseTest {
    private val preferencesRepository: PreferencesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetAppThemeUseCase

    @Before
    fun setUp() {
        useCase = GetAppThemeUseCase(preferencesRepository)
    }

    @Test
    fun `should return dark theme when dark mode is enabled`() = runTest {
        every { preferencesRepository.getAppDarkModeOn() } returns flowOf(AppTheme.DARK)

        val result = useCase.invoke().first()

        assertThat(result).isEqualTo(AppTheme.DARK)
        verify(exactly = 1) { preferencesRepository.getAppDarkModeOn() }
    }

    @Test
    fun `should return light theme when dark mode is disabled`() = runTest {
        every { preferencesRepository.getAppDarkModeOn() } returns flowOf(AppTheme.LIGHT)

        val result = useCase.invoke().first()

        assertThat(result).isEqualTo(AppTheme.LIGHT)
        verify(exactly = 1) { preferencesRepository.getAppDarkModeOn() }
    }

    @Test
    fun `should return flow from repository mapped to themes`() = runTest {
        every { preferencesRepository.getAppDarkModeOn() } returns flowOf(AppTheme.DARK)

        val result = useCase.invoke().first()

        assertThat(result).isEqualTo(AppTheme.DARK)
        verify(exactly = 1) { preferencesRepository.getAppDarkModeOn() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        every { preferencesRepository.getAppDarkModeOn() } throws RuntimeException("Preferences error")

        useCase.invoke().first()
    }
}