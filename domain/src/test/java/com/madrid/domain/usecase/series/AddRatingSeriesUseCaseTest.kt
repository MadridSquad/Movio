package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddRatingSeriesUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: AddRatingSeriesUseCase

    @Before
    fun setUp() {
        useCase = AddRatingSeriesUseCase(seriesRepository)
    }

    @Test
    fun `should call repository addRatingSeries with movieId and rate`() = runTest {
        val movieId = 1
        val rate = 4.5
        coEvery { seriesRepository.addRatingSeries(movieId, rate) } returns Unit

        val result = useCase.invoke(movieId, rate)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { seriesRepository.addRatingSeries(movieId, rate) }
    }

    @Test
    fun `should call repository addRatingSeries with minimum rate`() = runTest {
        val movieId = 123
        val rate = 0.0
        coEvery { seriesRepository.addRatingSeries(movieId, rate) } returns Unit

        val result = useCase.invoke(movieId, rate)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { seriesRepository.addRatingSeries(movieId, rate) }
    }

    @Test
    fun `should call repository addRatingSeries with maximum rate`() = runTest {
        val movieId = 456
        val rate = 10.0
        coEvery { seriesRepository.addRatingSeries(movieId, rate) } returns Unit

        val result = useCase.invoke(movieId, rate)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { seriesRepository.addRatingSeries(movieId, rate) }
    }

    @Test
    fun `should call repository addRatingSeries with negative movieId`() = runTest {
        val movieId = -1
        val rate = 3.5
        coEvery { seriesRepository.addRatingSeries(movieId, rate) } returns Unit

        val result = useCase.invoke(movieId, rate)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { seriesRepository.addRatingSeries(movieId, rate) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val movieId = 1
        val rate = 5.0
        coEvery {
            seriesRepository.addRatingSeries(
                movieId,
                rate
            )
        } throws RuntimeException("Rating error")

        useCase.invoke(movieId, rate)
    }

    @Test
    fun `should call repository addRatingSeries with decimal rate`() = runTest {
        val movieId = 789
        val rate = 7.25
        coEvery { seriesRepository.addRatingSeries(movieId, rate) } returns Unit

        val result = useCase.invoke(movieId, rate)

        assertThat(result).isEqualTo(Unit)
        coVerify(exactly = 1) { seriesRepository.addRatingSeries(movieId, rate) }
    }
}