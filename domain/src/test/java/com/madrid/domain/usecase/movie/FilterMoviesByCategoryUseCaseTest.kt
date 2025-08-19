package com.madrid.domain.usecase.movie

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import org.junit.Before
import org.junit.Test

class FilterMoviesByCategoryUseCaseTest {
    private lateinit var useCase: FilterMoviesByCategoryUseCase

    @Before
    fun setUp() {
        useCase = FilterMoviesByCategoryUseCase()
    }

    @Test
    fun `should return movies matching the specified category`() {
        val movieList = listOf(testMovieAction, testMovieComedy, testMovieActionComedy)

        val result = useCase.invoke(movieList, 1) // Action category

        assertThat(result).containsExactly(testMovieAction, testMovieActionComedy)
    }

    @Test
    fun `should return empty list when no movies match category`() {
        val movieList = listOf(testMovieAction, testMovieComedy)

        val result = useCase.invoke(movieList, 999) // Non-existent category

        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when input list is empty`() {
        val result = useCase.invoke(emptyList(), 1)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should return all movies when all match the category`() {
        val movieList = listOf(testMovieAction, testMovieActionComedy)

        val result = useCase.invoke(movieList, 1) // Action category

        assertThat(result).containsExactly(testMovieAction, testMovieActionComedy)
    }

    @Test
    fun `should handle movies with multiple genres correctly`() {
        val movieList = listOf(testMovieActionComedy)

        val resultAction = useCase.invoke(movieList, 1) // Action
        val resultComedy = useCase.invoke(movieList, 2) // Comedy

        assertThat(resultAction).containsExactly(testMovieActionComedy)
        assertThat(resultComedy).containsExactly(testMovieActionComedy)
    }

    @Test
    fun `should return correct movies for different categories`() {
        val movieList = listOf(testMovieAction, testMovieComedy, testMovieActionComedy)

        val actionResult = useCase.invoke(movieList, 1)
        val comedyResult = useCase.invoke(movieList, 2)

        assertThat(actionResult).containsExactly(testMovieAction, testMovieActionComedy)
        assertThat(comedyResult).containsExactly(testMovieComedy, testMovieActionComedy)
    }

    @Test
    fun `should handle single movie with matching category`() {
        val movieList = listOf(testMovieAction)

        val result = useCase.invoke(movieList, 1)

        assertThat(result).containsExactly(testMovieAction)
    }

    private companion object {
        val actionGenre = Genre(id = 1, name = "Action")
        val comedyGenre = Genre(id = 2, name = "Comedy")

        val testMovieAction = Movie(
            id = 1,
            title = "Action Movie",
            imageUrl = "/action_poster.jpg",
            rate = 7.5,
            releaseDate = "2023-01-01",
            movieDuration = "120 min",
            description = "An action-packed movie",
            genre = listOf(actionGenre)
        )

        val testMovieComedy = Movie(
            id = 2,
            title = "Comedy Movie",
            imageUrl = "/comedy_poster.jpg",
            rate = 6.8,
            releaseDate = "2023-02-01",
            movieDuration = "105 min",
            description = "A hilarious comedy",
            genre = listOf(comedyGenre)
        )

        val testMovieActionComedy = Movie(
            id = 3,
            title = "Action Comedy Movie",
            imageUrl = "/action_comedy_poster.jpg",
            rate = 7.2,
            releaseDate = "2023-03-01",
            movieDuration = "135 min",
            description = "Action and comedy combined",
            genre = listOf(actionGenre, comedyGenre)
        )
    }
}