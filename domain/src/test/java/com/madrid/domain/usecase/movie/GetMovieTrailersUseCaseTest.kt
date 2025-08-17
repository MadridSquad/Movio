package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieTrailersUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMovieTrailersUseCase

    @Before
    fun setUp() {
        useCase = GetMovieTrailersUseCase(movieRepository)
    }

    @Test
    fun `invoke SHOULD return trailers list from repository`() = runTest {
        coEvery { movieRepository.getMovieTrailersById(123) } returns testTrailers

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEqualTo(testTrailers)
        coVerify(exactly = 1) { movieRepository.getMovieTrailersById(123) }
    }

    @Test
    fun `invoke SHOULD return correct trailers for different movie id`() = runTest {
        val anotherTrailers = listOf(testTrailers.first().copy(id = "456", key = "another_key"))
        coEvery { movieRepository.getMovieTrailersById(456) } returns anotherTrailers

        val result = useCase.invoke(456)

        Truth.assertThat(result).isEqualTo(anotherTrailers)
        coVerify(exactly = 1) { movieRepository.getMovieTrailersById(456) }
    }

    @Test
    fun `invoke SHOULD return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getMovieTrailersById(123) } returns emptyList()

        val result = useCase.invoke(123)

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMovieTrailersById(123) }
    }

    @Test
    fun `invoke SHOULD return single trailer when repository returns single trailer`() = runTest {
        val singleTrailer = listOf(testTrailers.first())
        coEvery { movieRepository.getMovieTrailersById(123) } returns singleTrailer

        val result = useCase.invoke(123)

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result).isEqualTo(singleTrailer)
        coVerify(exactly = 1) { movieRepository.getMovieTrailersById(123) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMovieTrailersById(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke SHOULD throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.getMovieTrailersById(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `invoke SHOULD call repository with correct movie id`() = runTest {
        coEvery { movieRepository.getMovieTrailersById(999) } returns testTrailers

        useCase.invoke(999)

        coVerify(exactly = 1) { movieRepository.getMovieTrailersById(999) }
    }

    private companion object {
        val testTrailers = listOf(
            Trailer(
                key = "test_key_1",
                id = "trailer_1"
            ),
            Trailer(
                key = "test_key_2",
                id = "trailer_2"
            ),
            Trailer(
                key = "test_key_3",
                id = "trailer_3"
            )
        )
    }
}