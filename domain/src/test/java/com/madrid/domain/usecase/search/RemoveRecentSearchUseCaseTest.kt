package com.madrid.domain.usecase.search

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoveRecentSearchUseCaseTest {
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: RemoveRecentSearchUseCase

    @Before
    fun setUp() {
        useCase = RemoveRecentSearchUseCase(searchRepository)
    }

    @Test
    fun `should call repository removeRecentSearchByQuery with item`() = runTest {
        val item = "Batman"
        val expectedResult = Unit
        coEvery { searchRepository.removeRecentSearchByQuery(item) } returns expectedResult

        val result = useCase.invoke(item)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.removeRecentSearchByQuery(item) }
    }

    @Test
    fun `should call repository removeRecentSearchByQuery with empty string`() = runTest {
        val item = ""
        val expectedResult = Unit
        coEvery { searchRepository.removeRecentSearchByQuery(item) } returns expectedResult

        val result = useCase.invoke(item)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.removeRecentSearchByQuery(item) }
    }

    @Test
    fun `should call repository removeRecentSearchByQuery with long string`() = runTest {
        val item = "Very long search query with multiple words and characters"
        val expectedResult = Unit
        coEvery { searchRepository.removeRecentSearchByQuery(item) } returns expectedResult

        val result = useCase.invoke(item)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.removeRecentSearchByQuery(item) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val item = "Test"
        coEvery { searchRepository.removeRecentSearchByQuery(item) } throws RuntimeException("Remove search error")

        useCase.invoke(item)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val item = "Spider-Man"
        val expectedResult = Unit
        coEvery { searchRepository.removeRecentSearchByQuery(item) } returns expectedResult

        val result = useCase.invoke(item)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.removeRecentSearchByQuery(item) }
    }

    @Test
    fun `should handle special characters in item`() = runTest {
        val item = "Action & Adventure @2024!"
        val expectedResult = Unit
        coEvery { searchRepository.removeRecentSearchByQuery(item) } returns expectedResult

        val result = useCase.invoke(item)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.removeRecentSearchByQuery(item) }
    }
}