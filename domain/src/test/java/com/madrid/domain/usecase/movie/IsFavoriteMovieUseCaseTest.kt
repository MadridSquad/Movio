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

class IsFavoriteMovieUseCaseTest {
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = mockk(relaxed = true)
    private lateinit var useCase: IsFavoriteMovieUseCase

    @Before
    fun setUp() {
        useCase = IsFavoriteMovieUseCase(getFavoriteMoviesUseCase)
    }

    @Test
    fun `invoke SHOULD return true when movie is in favorites list`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } returns testMovies

        val result = useCase.invoke(123)

        Truth.assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteMoviesUseCase() }
    }

    @Test
    fun `invoke SHOULD return false when movie is not in favorites list`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } returns testMovies

        val result = useCase.invoke(999)

        Truth.assertThat(result).isFalse()
        coVerify(exactly = 1) { getFavoriteMoviesUseCase() }
    }

    @Test
    fun `invoke SHOULD return false when favorites list is empty`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } returns emptyList()

        val result = useCase.invoke(123)

        Truth.assertThat(result).isFalse()
        coVerify(exactly = 1) { getFavoriteMoviesUseCase() }
    }

    @Test
    fun `invoke SHOULD return true when movie id matches exactly`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } returns testMovies

        val result = useCase.invoke(124)

        Truth.assertThat(result).isTrue()
        coVerify(exactly = 1) { getFavoriteMoviesUseCase() }
    }

    @Test
    fun `invoke SHOULD return false for negative movie id when not in favorites`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } returns testMovies

        val result = useCase.invoke(-1)

        Truth.assertThat(result).isFalse()
        coVerify(exactly = 1) { getFavoriteMoviesUseCase() }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when getFavoriteMoviesUseCase fails`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } throws RuntimeException("Network error")

        useCase.invoke(123)
    }

    @Test
    fun `invoke SHOULD call getFavoriteMoviesUseCase exactly once`() = runTest {
        coEvery { getFavoriteMoviesUseCase() } returns testMovies

        useCase.invoke(123)

        coVerify(exactly = 1) { getFavoriteMoviesUseCase() }
    }

    private companion object {
        val testMovies = listOf(
            Movie(
                id = 123,
                title = "Favorite Movie 1",
                imageUrl = "/favorite_poster1.jpg",
                rate = 8.5,
                releaseDate = "2023-01-01",
                movieDuration = "120 min",
                description = "Favorite movie description 1",
                genre = listOf(Genre(id = 1, name = "Action")),
                trailer = Trailer(key = "", id = "")
            ),
            Movie(
                id = 124,
                title = "Favorite Movie 2",
                imageUrl = "/favorite_poster2.jpg",
                rate = 8.0,
                releaseDate = "2023-01-02",
                movieDuration = "135 min",
                description = "Favorite movie description 2",
                genre = listOf(Genre(id = 2, name = "Drama")),
                trailer = Trailer(key = "", id = "")
            )
        )
    }
}