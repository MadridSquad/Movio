package com.madrid.domain.usecase.search

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetRecentSearchesUseCaseTest {
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: GetRecentSearchesUseCase

    @Before
    fun setUp() {
        useCase = GetRecentSearchesUseCase(searchRepository)
    }

    @Test
    fun `should call repository getRecentSearches`() = runTest {
        val expectedResult = emptyList<String>()
        coEvery { searchRepository.getRecentSearches() } returns expectedResult

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getRecentSearches() }
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val expectedResult = listOf("Search1", "Search2", "Search3")
        coEvery { searchRepository.getRecentSearches() } returns expectedResult

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getRecentSearches() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { searchRepository.getRecentSearches() } throws RuntimeException("Recent searches error")

        useCase.invoke()
    }

    @Test
    fun `should call repository method exactly once on multiple calls`() = runTest {
        val expectedResult = listOf("Recent1", "Recent2")
        coEvery { searchRepository.getRecentSearches() } returns expectedResult

        useCase.invoke()
        useCase.invoke()

        coVerify(exactly = 2) { searchRepository.getRecentSearches() }
    }

    @Test
    fun `should handle repository call without parameters`() = runTest {
        val expectedResult = emptyList<String>()
        coEvery { searchRepository.getRecentSearches() } returns expectedResult

        val result = useCase.invoke()

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getRecentSearches() }
    }
}