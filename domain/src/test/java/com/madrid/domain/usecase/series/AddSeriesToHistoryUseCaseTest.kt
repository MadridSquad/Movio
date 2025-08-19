package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddSeriesToHistoryUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: AddSeriesToHistoryUseCase

    @Before
    fun setUp() {
        useCase = AddSeriesToHistoryUseCase(seriesRepository)
    }

    @Test
    fun `should call repository addSeriesToHistory with seriesId`() = runTest {
        val seriesId = 1
        val expectedResult = Unit
        coEvery { seriesRepository.addSeriesToHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.addSeriesToHistory(seriesId) }
    }

    @Test
    fun `should call repository addSeriesToHistory with positive seriesId`() = runTest {
        val seriesId = 123
        val expectedResult = Unit
        coEvery { seriesRepository.addSeriesToHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.addSeriesToHistory(seriesId) }
    }

    @Test
    fun `should call repository addSeriesToHistory with large seriesId`() = runTest {
        val seriesId = 999999
        val expectedResult = Unit
        coEvery { seriesRepository.addSeriesToHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.addSeriesToHistory(seriesId) }
    }

    @Test
    fun `should call repository addSeriesToHistory with zero seriesId`() = runTest {
        val seriesId = 0
        val expectedResult = Unit
        coEvery { seriesRepository.addSeriesToHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.addSeriesToHistory(seriesId) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val seriesId = 1
        coEvery { seriesRepository.addSeriesToHistory(seriesId) } throws RuntimeException("History error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val seriesId = 456
        val expectedResult = Unit
        coEvery { seriesRepository.addSeriesToHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.addSeriesToHistory(seriesId) }
    }
}