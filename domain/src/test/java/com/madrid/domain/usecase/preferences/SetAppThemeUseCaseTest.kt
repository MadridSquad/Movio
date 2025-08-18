package com.madrid.domain.usecase.preferences

import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppTheme
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetAppThemeUseCaseTest {
    private val preferencesRepository: PreferencesRepository = mockk(relaxed = true)
    private lateinit var useCase: SetAppThemeUseCase

    @Before
    fun setUp() {
        useCase = SetAppThemeUseCase(preferencesRepository)
    }

    @Test
    fun `should call repository setAppDarkModeOn with dark theme`() = runTest {
        useCase.invoke(AppTheme.DARK)

        coVerify(exactly = 1) { preferencesRepository.setAppDarkModeOn(AppTheme.DARK) }
    }

    @Test
    fun `should call repository setAppDarkModeOn with light theme`() = runTest {
        useCase.invoke(AppTheme.LIGHT)

        coVerify(exactly = 1) { preferencesRepository.setAppDarkModeOn(AppTheme.LIGHT) }
    }

    @Test
    fun `should call repository setAppDarkModeOn with system theme`() = runTest {
        useCase.invoke(AppTheme.LIGHT)

        coVerify(exactly = 1) { preferencesRepository.setAppDarkModeOn(AppTheme.LIGHT) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { preferencesRepository.setAppDarkModeOn(any()) } throws RuntimeException("Preferences error")

        useCase.invoke(AppTheme.DARK)
    }

    @Test
    fun `should complete successfully when repository succeeds`() = runTest {
        coEvery { preferencesRepository.setAppDarkModeOn(AppTheme.LIGHT) } returns Unit

        useCase.invoke(AppTheme.LIGHT)

        coVerify(exactly = 1) { preferencesRepository.setAppDarkModeOn(AppTheme.LIGHT) }
    }
}