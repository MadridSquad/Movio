package com.madrid.domain.usecase.preferences

import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppLanguage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetLanguageUseCaseTest {
    private val preferencesRepository: PreferencesRepository = mockk(relaxed = true)
    private lateinit var useCase: SetLanguageUseCase

    @Before
    fun setUp() {
        useCase = SetLanguageUseCase(preferencesRepository)
    }

    @Test
    fun `should call repository setAppLanguage with English`() = runTest {
        useCase.invoke(AppLanguage.ENGLISH)

        coVerify(exactly = 1) { preferencesRepository.setAppLanguage(AppLanguage.ENGLISH) }
    }

    @Test
    fun `should call repository setAppLanguage with Arabic`() = runTest {
        useCase.invoke(AppLanguage.ARABIC)

        coVerify(exactly = 1) { preferencesRepository.setAppLanguage(AppLanguage.ARABIC) }
    }


    @Test
    fun `should call repository setAppLanguage with system language`() = runTest {
        useCase.invoke(AppLanguage.ENGLISH)

        coVerify(exactly = 1) { preferencesRepository.setAppLanguage(AppLanguage.ENGLISH) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { preferencesRepository.setAppLanguage(any()) } throws RuntimeException("Preferences error")

        useCase.invoke(AppLanguage.ENGLISH)
    }

    @Test
    fun `should complete successfully when repository succeeds`() = runTest {
        coEvery { preferencesRepository.setAppLanguage(AppLanguage.ARABIC) } returns Unit

        useCase.invoke(AppLanguage.ARABIC)

        coVerify(exactly = 1) { preferencesRepository.setAppLanguage(AppLanguage.ARABIC) }
    }
}