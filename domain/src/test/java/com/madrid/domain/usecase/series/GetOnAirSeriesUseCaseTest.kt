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

class GetOnAirSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetOnAirSeriesUseCase

    @Before
    fun setUp() {
        useCase = GetOnAirSeriesUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getOnAirSeries with page`() = runTest {
        val page = 1
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getOnAirSeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getOnAirSeries(page) }
    }

    @Test
    fun `should call repository getOnAirSeries with first page`() = runTest {
        val page = 1
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getOnAirSeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getOnAirSeries(page) }
    }

    @Test
    fun `should call repository getOnAirSeries with multiple pages`() = runTest {
        val page = 5
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getOnAirSeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getOnAirSeries(page) }
    }

    @Test
    fun `should call repository getOnAirSeries with large page number`() = runTest {
        val page = 999
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getOnAirSeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getOnAirSeries(page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val page = 1
        coEvery { seriesRepository.getOnAirSeries(page) } throws RuntimeException("Network error")

        useCase.invoke(page)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val page = 2
        val expectedResult = emptyList<Series>()
        coEvery { seriesRepository.getOnAirSeries(page) } returns expectedResult

        val result = useCase.invoke(page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.getOnAirSeries(page) }
    }
}