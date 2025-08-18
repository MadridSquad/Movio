package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteSeriesFromHistoryUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: DeleteSeriesFromHistoryUseCase

    @Before
    fun setUp() {
        useCase = DeleteSeriesFromHistoryUseCase(seriesRepository)
    }

    @Test
    fun `should call repository deleteSeriesFromHistory with seriesId`() = runTest {
        val seriesId = 1
        val expectedResult = Unit
        coEvery { seriesRepository.deleteSeriesFromHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.deleteSeriesFromHistory(seriesId) }
    }

    @Test
    fun `should call repository deleteSeriesFromHistory with positive seriesId`() = runTest {
        val seriesId = 123
        val expectedResult = Unit
        coEvery { seriesRepository.deleteSeriesFromHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.deleteSeriesFromHistory(seriesId) }
    }

    @Test
    fun `should call repository deleteSeriesFromHistory with large seriesId`() = runTest {
        val seriesId = 999999
        val expectedResult = Unit
        coEvery { seriesRepository.deleteSeriesFromHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.deleteSeriesFromHistory(seriesId) }
    }

    @Test
    fun `should call repository deleteSeriesFromHistory with zero seriesId`() = runTest {
        val seriesId = 0
        val expectedResult = Unit
        coEvery { seriesRepository.deleteSeriesFromHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.deleteSeriesFromHistory(seriesId) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val seriesId = 1
        coEvery { seriesRepository.deleteSeriesFromHistory(seriesId) } throws RuntimeException("Delete history error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val seriesId = 456
        val expectedResult = Unit
        coEvery { seriesRepository.deleteSeriesFromHistory(seriesId) } returns expectedResult

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { seriesRepository.deleteSeriesFromHistory(seriesId) }
    }
}