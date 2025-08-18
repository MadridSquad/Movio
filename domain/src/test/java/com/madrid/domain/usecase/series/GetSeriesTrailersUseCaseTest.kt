package com.madrid.domain.usecase.series

import com.google.common.truth.Truth
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesTrailersUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesTrailersUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesTrailersUseCase(seriesRepository)
    }

    @Test
    fun `invoke SHOULD call repository getSeriesTrailersById twice and return list`() = runTest {
        val seriesId = 123
        val expectedTrailers = listOf(
            Trailer(key = "trailer1", id = "1"),
            Trailer(key = "trailer2", id = "2")
        )
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } returns expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }

    @Test
    fun `invoke SHOULD return empty list when no trailers found`() = runTest {
        val seriesId = 456
        val expectedTrailers = emptyList<Trailer>()
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } returns expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }

    @Test
    fun `invoke SHOULD return single trailer`() = runTest {
        val seriesId = 789
        val expectedTrailers = listOf(Trailer(key = "mainTrailer", id = "1"))
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } returns expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result.first().key).isEqualTo("mainTrailer")
        Truth.assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }

    @Test
    fun `invoke SHOULD return multiple trailers`() = runTest {
        val seriesId = 111
        val expectedTrailers = (1..5).map {
            Trailer(key = "trailer$it", id = it.toString())
        }
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } returns expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).hasSize(5)
        Truth.assertThat(result).containsExactlyElementsIn(expectedTrailers)
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }

    @Test
    fun `invoke SHOULD handle exception in try block and still return trailers`() = runTest {
        val seriesId = 222
        val expectedTrailers = listOf(Trailer(key = "recoveryTrailer", id = "1"))
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } throws RuntimeException("First call error") andThen expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when both repository calls fail`() = runTest {
        val seriesId = 333
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } throws RuntimeException("Network error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `invoke SHOULD handle different seriesId values`() = runTest {
        val seriesId = 0
        val expectedTrailers = listOf(Trailer(key = "zeroSeriesTrailer", id = "1"))
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } returns expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).isEqualTo(expectedTrailers)
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }

    @Test
    fun `invoke SHOULD return trailers in correct order`() = runTest {
        val seriesId = 444
        val expectedTrailers = listOf(
            Trailer(key = "thirdTrailer", id = "3"),
            Trailer(key = "firstTrailer", id = "1"),
            Trailer(key = "secondTrailer", id = "2")
        )
        coEvery { seriesRepository.getSeriesTrailersById(seriesId) } returns expectedTrailers

        val result = useCase.invoke(seriesId)

        Truth.assertThat(result).containsExactlyElementsIn(expectedTrailers).inOrder()
        coVerify(exactly = 2) { seriesRepository.getSeriesTrailersById(seriesId) }
    }
}