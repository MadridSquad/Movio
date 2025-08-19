package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAiringTodaySeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetAiringTodaySeriesUseCase

    @Before
    fun setUp() {
        useCase = GetAiringTodaySeriesUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getAiringTodaySeries with page`() = runTest {
        val page = 1
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getAiringTodaySeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getAiringTodaySeries(page) }
    }

    @Test
    fun `should call repository getAiringTodaySeries with first page`() = runTest {
        val page = 1
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getAiringTodaySeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getAiringTodaySeries(page) }
    }

    @Test
    fun `should call repository getAiringTodaySeries with multiple pages`() = runTest {
        val page = 5
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getAiringTodaySeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getAiringTodaySeries(page) }
    }

    @Test
    fun `should call repository getAiringTodaySeries with large page number`() = runTest {
        val page = 999
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getAiringTodaySeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getAiringTodaySeries(page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val page = 1
        coEvery { seriesRepository.getAiringTodaySeries(page) } throws RuntimeException("Network error")

        useCase.invoke(page)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val page = 2
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getAiringTodaySeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getAiringTodaySeries(page) }
    }
}