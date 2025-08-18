package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddRecentSearchUseCaseTest {
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: AddRecentSearchUseCase

    @Before
    fun setUp() {
        useCase = AddRecentSearchUseCase(searchRepository)
    }

    @Test
    fun `should call repository addRecentSearchByQuery with search query`() = runTest {
        val searchQuery = "Batman"

        useCase.invoke(searchQuery)

        coVerify(exactly = 1) { searchRepository.addRecentSearchByQuery(searchQuery) }
    }

    @Test
    fun `should call repository addRecentSearchByQuery with different search query`() =
        runTest {
            val searchQuery = "Spider-Man"

            useCase.invoke(searchQuery)

            coVerify(exactly = 1) { searchRepository.addRecentSearchByQuery(searchQuery) }
        }

    @Test
    fun `should call repository addRecentSearchByQuery with empty string`() = runTest {
        val searchQuery = ""

        useCase.invoke(searchQuery)

        coVerify(exactly = 1) { searchRepository.addRecentSearchByQuery(searchQuery) }
    }

    @Test
    fun `should call repository addRecentSearchByQuery with whitespace string`() = runTest {
        val searchQuery = "   "

        useCase.invoke(searchQuery)

        coVerify(exactly = 1) { searchRepository.addRecentSearchByQuery(searchQuery) }
    }

    @Test
    fun `should call repository addRecentSearchByQuery with special characters`() = runTest {
        val searchQuery = "Movie & TV Show #1"

        useCase.invoke(searchQuery)

        coVerify(exactly = 1) { searchRepository.addRecentSearchByQuery(searchQuery) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val searchQuery = "Batman"
        coEvery { searchRepository.addRecentSearchByQuery(searchQuery) } throws RuntimeException("Search error")

        useCase.invoke(searchQuery)
    }

    @Test
    fun `should complete successfully when repository succeeds`() = runTest {
        val searchQuery = "Avengers"
        coEvery { searchRepository.addRecentSearchByQuery(searchQuery) } returns Unit

        useCase.invoke(searchQuery)

        coVerify(exactly = 1) { searchRepository.addRecentSearchByQuery(searchQuery) }
    }
}