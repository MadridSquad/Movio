package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Review
import com.madrid.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieReviewsUseCaseTest {
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private lateinit var useCase: GetMovieReviewsUseCase

    @Before
    fun setUp() {
        useCase = GetMovieReviewsUseCase(movieRepository)
    }

    @Test
    fun `should return reviews list from repository`() = runTest {
        coEvery { movieRepository.getMovieReviewsById(123) } returns testReviews

        val result = useCase.invoke(123)

        assertThat(result).isEqualTo(testReviews)
        coVerify(exactly = 1) { movieRepository.getMovieReviewsById(123) }
    }

    @Test
    fun `should return correct reviews for different movie id`() = runTest {
        val anotherReviews =
            listOf(testReviews.first().copy(reviewId = "456", comment = "Another review"))
        coEvery { movieRepository.getMovieReviewsById(456) } returns anotherReviews

        val result = useCase.invoke(456)

        assertThat(result).isEqualTo(anotherReviews)
        coVerify(exactly = 1) { movieRepository.getMovieReviewsById(456) }
    }

    @Test
    fun `should return empty list when repository returns empty list`() = runTest {
        coEvery { movieRepository.getMovieReviewsById(123) } returns emptyList()

        val result = useCase.invoke(123)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { movieRepository.getMovieReviewsById(123) }
    }

    @Test
    fun `should return single review when repository returns single review`() = runTest {
        val singleReview = listOf(testReviews.first())
        coEvery { movieRepository.getMovieReviewsById(123) } returns singleReview

        val result = useCase.invoke(123)

        assertThat(result).hasSize(1)
        assertThat(result).isEqualTo(singleReview)
        coVerify(exactly = 1) { movieRepository.getMovieReviewsById(123) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { movieRepository.getMovieReviewsById(123) } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception when repository throws IllegalArgumentException`() =
        runTest {
            coEvery { movieRepository.getMovieReviewsById(-1) } throws IllegalArgumentException("Invalid movie ID")

            useCase.invoke(-1)
        }

    @Test
    fun `should call repository with correct movie id`() = runTest {
        coEvery { movieRepository.getMovieReviewsById(999) } returns testReviews

        useCase.invoke(999)

        coVerify(exactly = 1) { movieRepository.getMovieReviewsById(999) }
    }

    private companion object {
        val testReviews = listOf(
            Review(
                reviewId = "123",
                reviewerName = "Test Author",
                reviewerPhotoUrl = "https://example.com/photo1.jpg",
                rate = 8.5,
                date = "2023-01-01",
                comment = "Great movie!"
            ),
            Review(
                reviewId = "124",
                reviewerName = "Another Author",
                reviewerPhotoUrl = "https://example.com/photo2.jpg",
                rate = 7.0,
                date = "2023-01-02",
                comment = "Good story and acting."
            )
        )
    }
}