package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Trailer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMoviesWithTrailersTest {
    private val movieTrailersUseCase: GetMovieTrailersUseCase = mockk(relaxed = true)
    private lateinit var useCase: GetMoviesWithTrailers

    @Before
    fun setUp() {
        useCase = GetMoviesWithTrailers(movieTrailersUseCase)
    }

    @Test
    fun `invoke SHOULD return movies with trailers when trailers exist`() = runTest {
        coEvery { movieTrailersUseCase(123) } returns listOf(testTrailer)
        coEvery { movieTrailersUseCase(124) } returns listOf(testTrailer2)

        val result = useCase.invoke(testMovies)

        Truth.assertThat(result).hasSize(2)
        Truth.assertThat(result[0].trailer?.key).isEqualTo("test_key_1")
        Truth.assertThat(result[0].trailer?.id).isEqualTo("trailer_1")
        Truth.assertThat(result[1].trailer?.key).isEqualTo("test_key_2")
        Truth.assertThat(result[1].trailer?.id).isEqualTo("trailer_2")
        coVerify(exactly = 1) { movieTrailersUseCase(123) }
        coVerify(exactly = 1) { movieTrailersUseCase(124) }
    }

    @Test
    fun `invoke SHOULD return movies with empty trailers when no trailers found`() = runTest {
        coEvery { movieTrailersUseCase(123) } returns emptyList()
        coEvery { movieTrailersUseCase(124) } returns emptyList()

        val result = useCase.invoke(testMovies)

        Truth.assertThat(result).hasSize(2)
        Truth.assertThat(result[0].trailer?.key).isEmpty()
        Truth.assertThat(result[0].trailer?.id).isEmpty()
        Truth.assertThat(result[1].trailer?.key).isEmpty()
        Truth.assertThat(result[1].trailer?.id).isEmpty()
        coVerify(exactly = 1) { movieTrailersUseCase(123) }
        coVerify(exactly = 1) { movieTrailersUseCase(124) }
    }

    @Test
    fun `invoke SHOULD return empty list when input movies list is empty`() = runTest {
        val result = useCase.invoke(emptyList())

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 0) { movieTrailersUseCase(any()) }
    }

    @Test
    fun `invoke SHOULD use first trailer when multiple trailers exist`() = runTest {
        val multipleTrailers = listOf(testTrailer, testTrailer2)
        coEvery { movieTrailersUseCase(123) } returns multipleTrailers

        val result = useCase.invoke(listOf(testMovies[0]))

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result[0].trailer?.key).isEqualTo("test_key_1")
        Truth.assertThat(result[0].trailer?.id).isEqualTo("trailer_1")
        coVerify(exactly = 1) { movieTrailersUseCase(123) }
    }

    @Test
    fun `invoke SHOULD handle single movie with trailer`() = runTest {
        coEvery { movieTrailersUseCase(123) } returns listOf(testTrailer)

        val result = useCase.invoke(listOf(testMovies[0]))

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result[0].trailer?.key).isEqualTo("test_key_1")
        Truth.assertThat(result[0].trailer?.id).isEqualTo("trailer_1")
        coVerify(exactly = 1) { movieTrailersUseCase(123) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when movieTrailersUseCase fails`() = runTest {
        coEvery { movieTrailersUseCase(123) } throws RuntimeException("Network error")

        useCase.invoke(listOf(testMovies[0]))
    }

    @Test
    fun `invoke SHOULD call movieTrailersUseCase for each movie`() = runTest {
        coEvery { movieTrailersUseCase(any()) } returns listOf(testTrailer)

        useCase.invoke(testMovies)

        coVerify(exactly = 1) { movieTrailersUseCase(123) }
        coVerify(exactly = 1) { movieTrailersUseCase(124) }
    }

    private companion object {
        val testTrailer = Trailer(
            key = "test_key_1",
            id = "trailer_1"
        )

        val testTrailer2 = Trailer(
            key = "test_key_2",
            id = "trailer_2"
        )

        val testMovies = listOf(
            Movie(
                id = 123,
                title = "Test Movie 1",
                imageUrl = "/test_poster1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Test movie description 1",
                genre = listOf(Genre(id = 1, name = "Action")),
                trailer = Trailer(key = "", id = "")
            ),
            Movie(
                id = 124,
                title = "Test Movie 2",
                imageUrl = "/test_poster2.jpg",
                rate = 7.5,
                releaseDate = "2023-01-02",
                movieDuration = "105 min",
                description = "Test movie description 2",
                genre = listOf(Genre(id = 2, name = "Drama")),
                trailer = Trailer(key = "", id = "")
            )
        )
    }
}