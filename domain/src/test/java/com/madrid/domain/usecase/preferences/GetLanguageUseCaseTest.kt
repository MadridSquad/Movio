package com.madrid.domain.usecase.preferences

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.PreferencesRepository
import com.madrid.domain.utils.AppLanguage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class GetLanguageUseCaseTest {
    private val preferencesRepository: PreferencesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetLanguageUseCase

    @Before
    fun setUp() {
        useCase = GetLanguageUseCase(preferencesRepository)
    }

    @Test
    fun `should return language when repository returns language`() = runTest {
        every { preferencesRepository.getAppLanguage() } returns flowOf(AppLanguage.ENGLISH)

        val result = useCase.invoke().first()

        assertThat(result).isEqualTo(AppLanguage.ENGLISH)
        verify(exactly = 1) { preferencesRepository.getAppLanguage() }
    }

    @Test
    fun `should return different language when repository returns different language`() =
        runTest {
            every { preferencesRepository.getAppLanguage() } returns flowOf(AppLanguage.ARABIC)
            val result = useCase.invoke().first()

            assertThat(result).isEqualTo(AppLanguage.ARABIC)
            verify(exactly = 1) { preferencesRepository.getAppLanguage() }
        }

    @Test
    fun `should return empty string when repository returns empty string`() = runTest {
        every { preferencesRepository.getAppLanguage() } returns flowOf(AppLanguage.ENGLISH)
        val result = useCase.invoke().first()

        assertThat(result).isEqualTo(AppLanguage.ENGLISH)
        verify(exactly = 1) { preferencesRepository.getAppLanguage() }
    }

    @Test
    fun `should return flow from repository`() {
        val expectedFlow = flowOf(AppLanguage.ARABIC)
        every { preferencesRepository.getAppLanguage() } returns expectedFlow

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedFlow)
        verify(exactly = 1) { preferencesRepository.getAppLanguage() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        every { preferencesRepository.getAppLanguage() } throws RuntimeException("Preferences error")

        useCase.invoke().first()
    }
}