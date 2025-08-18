package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ClearAllRecentSearchesUseCaseTest {
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: ClearAllRecentSearchesUseCase

    @Before
    fun setUp() {
        useCase = ClearAllRecentSearchesUseCase(searchRepository)
    }

    @Test
    fun `should call repository clearAllRecentSearches`() = runTest {
        useCase.invoke()

        coVerify(exactly = 1) { searchRepository.clearAllRecentSearches() }
    }

    @Test
    fun `should complete successfully when repository succeeds`() = runTest {
        coEvery { searchRepository.clearAllRecentSearches() } returns Unit

        useCase.invoke()

        coVerify(exactly = 1) { searchRepository.clearAllRecentSearches() }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        coEvery { searchRepository.clearAllRecentSearches() } throws RuntimeException("Clear error")

        useCase.invoke()
    }

    @Test
    fun `should call repository method exactly once on multiple calls`() = runTest {
        useCase.invoke()
        useCase.invoke()

        coVerify(exactly = 2) { searchRepository.clearAllRecentSearches() }
    }

    @Test
    fun `should handle repository call without parameters`() = runTest {
        coEvery { searchRepository.clearAllRecentSearches() } returns Unit

        useCase.invoke()

        coVerify(exactly = 1) { searchRepository.clearAllRecentSearches() }
    }
}