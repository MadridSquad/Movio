package com.madrid.domain.usecase.series

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Review
import com.madrid.domain.repository.SeriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSeriesReviewsUseCaseTest {
    private val seriesRepository: SeriesRepository = mockk(relaxed = true)
    private lateinit var useCase: GetSeriesReviewsUseCase

    @Before
    fun setUp() {
        useCase = GetSeriesReviewsUseCase(seriesRepository)
    }

    @Test
    fun `should call repository getSeriesReviewsById and return list`() = runTest {
        val seriesId = 123
        val expectedReviews = listOf(
            Review(
                reviewId = "1",
                reviewerName = "User1",
                reviewerPhotoUrl = "",
                rate = 5.0,
                date = "2024-01-01",
                comment = "Great series!"
            ),
            Review(
                reviewId = "2",
                reviewerName = "User2",
                reviewerPhotoUrl = "",
                rate = 4.0,
                date = "2024-01-02",
                comment = "Good plot"
            )
        )
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedReviews)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }

    @Test
    fun `should return empty list when no reviews found`() = runTest {
        val seriesId = 456
        val expectedReviews = emptyList<Review>()
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).isEmpty()
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }

    @Test
    fun `should return single review`() = runTest {
        val seriesId = 789
        val expectedReviews = listOf(
            Review(
                reviewId = "1",
                reviewerName = "User1",
                reviewerPhotoUrl = "",
                rate = 5.0,
                date = "2024-01-01",
                comment = "Amazing show!"
            )
        )
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).hasSize(1)
        assertThat(result.first().comment).isEqualTo("Amazing show!")
        assertThat(result).isEqualTo(expectedReviews)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }

    @Test
    fun `should return multiple reviews`() = runTest {
        val seriesId = 111
        val expectedReviews = (1..10).map {
            Review(
                reviewId = it.toString(),
                reviewerName = "User$it",
                reviewerPhotoUrl = "",
                rate = (it % 5 + 1).toDouble(),
                date = "2024-01-01",
                comment = "Review $it"
            )
        }
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).hasSize(10)
        assertThat(result).containsExactlyElementsIn(expectedReviews)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }

    @Test
    fun `should handle different seriesId values`() = runTest {
        val seriesId = 0
        val expectedReviews = listOf(
            Review(
                reviewId = "1",
                reviewerName = "User1",
                reviewerPhotoUrl = "",
                rate = 3.0,
                date = "2024-01-01",
                comment = "Zero series review"
            )
        )
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedReviews)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }

    @Test
    fun `should handle large seriesId values`() = runTest {
        val seriesId = 999999
        val expectedReviews = listOf(
            Review(
                reviewId = "1",
                reviewerName = "User1",
                reviewerPhotoUrl = "",
                rate = 4.0,
                date = "2024-01-01",
                comment = "Large ID series"
            )
        )
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).isEqualTo(expectedReviews)
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val seriesId = 123
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } throws RuntimeException("Network error")

        useCase.invoke(seriesId)
    }

    @Test
    fun `should return reviews in correct order`() = runTest {
        val seriesId = 222
        val expectedReviews = listOf(
            Review(
                reviewId = "3",
                reviewerName = "User3",
                reviewerPhotoUrl = "",
                rate = 2.0,
                date = "2024-01-01",
                comment = "Third review"
            ),
            Review(
                reviewId = "1",
                reviewerName = "User1",
                reviewerPhotoUrl = "",
                rate = 5.0,
                date = "2024-01-01",
                comment = "First review"
            ),
            Review(
                reviewId = "2",
                reviewerName = "User2",
                reviewerPhotoUrl = "",
                rate = 3.0,
                date = "2024-01-01",
                comment = "Second review"
            )
        )
        coEvery { seriesRepository.getSeriesReviewsById(seriesId) } returns expectedReviews

        val result = useCase.invoke(seriesId)

        assertThat(result).containsExactlyElementsIn(expectedReviews).inOrder()
        coVerify(exactly = 1) { seriesRepository.getSeriesReviewsById(seriesId) }
    }
}