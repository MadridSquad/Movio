package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.Trailer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesWithTrailersUseCaseTest {
    private val getSeriesTrailersUseCase: GetSeriesTrailersUseCase = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesWithTrailersUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesWithTrailersUseCase(getSeriesTrailersUseCase)
    }

    @Test
    fun `should add trailer to series when trailers exist`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Series 1",
                imageUrl = "https://example.com/image1.jpg",
                rate = 8.0,
                airDate = "2023-01-01",
                seasons = emptyList(),
                description = "Description 1",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Series 2",
                imageUrl = "https://example.com/image2.jpg",
                rate = 7.5,
                airDate = "2023-02-01",
                seasons = emptyList(),
                description = "Description 2",
                genre = emptyList()
            )
        )
        val trailers1 = listOf(
            Trailer(id = "10", key = "trailer1"),
            Trailer(id = "11", key = "trailer2")
        )
        val trailers2 = listOf(Trailer(id = "20", key = "trailer3"))

        coEvery { getSeriesTrailersUseCase(1) } returns trailers1
        coEvery { getSeriesTrailersUseCase(2) } returns trailers2

        val result = useCase.invoke(series)

        assertThat(result).hasSize(2)
        assertThat(result[0].trailer?.id).isEqualTo("10")
        assertThat(result[0].trailer?.key).isEqualTo("trailer1")
        assertThat(result[1].trailer?.id).isEqualTo("20")
        assertThat(result[1].trailer?.key).isEqualTo("trailer3")
        coVerify(exactly = 2) { getSeriesTrailersUseCase(1) }
        coVerify(exactly = 2) { getSeriesTrailersUseCase(2) }
    }

    @Test
    fun `should return series unchanged when no trailers exist`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Series 2",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )

        coEvery { getSeriesTrailersUseCase(1) } returns emptyList()
        coEvery { getSeriesTrailersUseCase(2) } returns emptyList()

        val result = useCase.invoke(series)

        assertThat(result).hasSize(2)
        assertThat(result[0].trailer).isNull()
        assertThat(result[1].trailer).isNull()
        assertThat(result[0].title).isEqualTo("Series 1")
        assertThat(result[1].title).isEqualTo("Series 2")
        coVerify(exactly = 1) { getSeriesTrailersUseCase(1) }
        coVerify(exactly = 1) { getSeriesTrailersUseCase(2) }
    }

    @Test
    fun `should handle mixed scenarios with some series having trailers`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 2,
                title = "Series 2",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            ),
            Series(
                id = 3,
                title = "Series 3",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        val trailers1 = listOf(Trailer(id = "10", key = "trailer1"))

        coEvery { getSeriesTrailersUseCase(1) } returns trailers1
        coEvery { getSeriesTrailersUseCase(2) } returns emptyList()
        coEvery { getSeriesTrailersUseCase(3) } returns trailers1

        val result = useCase.invoke(series)

        assertThat(result).hasSize(3)
        assertThat(result[0].trailer?.id).isEqualTo("10")
        assertThat(result[1].trailer).isNull()
        assertThat(result[2].trailer?.id).isEqualTo("10")
        coVerify(exactly = 2) { getSeriesTrailersUseCase(1) }
        coVerify(exactly = 1) { getSeriesTrailersUseCase(2) }
        coVerify(exactly = 2) { getSeriesTrailersUseCase(3) }
    }

    @Test
    fun `should return empty list when input is empty`() = runTest {
        val series = emptyList<Series>()

        val result = useCase.invoke(series)

        assertThat(result).isEmpty()
        coVerify(exactly = 0) { getSeriesTrailersUseCase(any()) }
    }

    @Test
    fun `should handle single series with trailer`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Single Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        val trailers = listOf(Trailer(id = "100", key = "singleTrailer"))

        coEvery { getSeriesTrailersUseCase(1) } returns trailers

        val result = useCase.invoke(series)

        assertThat(result).hasSize(1)
        assertThat(result.first().trailer?.id).isEqualTo("100")
        assertThat(result.first().trailer?.key).isEqualTo("singleTrailer")
        coVerify(exactly = 2) { getSeriesTrailersUseCase(1) }
    }

    @Test
    fun `should handle single series without trailer`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Single Series",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )

        coEvery { getSeriesTrailersUseCase(1) } returns emptyList()

        val result = useCase.invoke(series)

        assertThat(result).hasSize(1)
        assertThat(result.first().trailer).isNull()
        assertThat(result.first().title).isEqualTo("Single Series")
        coVerify(exactly = 1) { getSeriesTrailersUseCase(1) }
    }

    @Test
    fun `should use only first trailer when multiple exist`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )
        val trailers = listOf(
            Trailer(id = "10", key = "firstTrailer"),
            Trailer(id = "11", key = "secondTrailer"),
            Trailer(id = "12", key = "thirdTrailer")
        )

        coEvery { getSeriesTrailersUseCase(1) } returns trailers

        val result = useCase.invoke(series)

        assertThat(result).hasSize(1)
        assertThat(result.first().trailer?.id).isEqualTo("10")
        assertThat(result.first().trailer?.key).isEqualTo("firstTrailer")
        coVerify(exactly = 2) { getSeriesTrailersUseCase(1) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when getSeriesTrailersUseCase fails`() = runTest {
        val series = listOf(
            Series(
                id = 1,
                title = "Series 1",
                imageUrl = "",
                rate = 0.0,
                airDate = "",
                seasons = emptyList(),
                description = "",
                genre = emptyList()
            )
        )

        coEvery { getSeriesTrailersUseCase(1) } throws RuntimeException("Network error")

        useCase.invoke(series)
    }

    @Test
    fun `should preserve series properties when adding trailer`() = runTest {
        val originalSeries = Series(
            id = 1,
            title = "Original Title",
            imageUrl = "",
            rate = 8.5,
            airDate = "",
            seasons = emptyList(),
            description = "Original Overview",
            genre = emptyList()
        )
        val series = listOf(originalSeries)
        val trailers = listOf(Trailer(id = "10", key = "trailer1"))

        coEvery { getSeriesTrailersUseCase(1) } returns trailers

        val result = useCase.invoke(series)

        assertThat(result.first().id).isEqualTo(1)
        assertThat(result.first().title).isEqualTo("Original Title")
        assertThat(result.first().description).isEqualTo("Original Overview")
        assertThat(result.first().rate).isEqualTo(8.5)
        assertThat(result.first().trailer?.id).isEqualTo("10")
        assertThat(result.first().trailer?.key).isEqualTo("trailer1")
    }
}