package com.madrid.domain.usecase.search

import com.google.common.truth.Truth.assertThat
import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetArtistsByQueryUseCaseTest {
    private val searchRepository: SearchRepository = mockk(relaxed = true)
    private lateinit var useCase: GetArtistsByQueryUseCase

    @Before
    fun setUp() {
        useCase = GetArtistsByQueryUseCase(searchRepository)
    }

    @Test
    fun `should call repository getArtistsByQuery with query and default page`() = runTest {
        val query = "Madonna"
        val expectedResult = emptyList<Artist>()
        coEvery { searchRepository.getArtistsByQuery(query, 1) } returns expectedResult

        val result = useCase.invoke(query)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getArtistsByQuery(query, 1) }
    }

    @Test
    fun `should call repository getArtistsByQuery with query and specific page`() = runTest {
        val query = "Beatles"
        val page = 2
        val expectedResult = emptyList<Artist>()
        coEvery { searchRepository.getArtistsByQuery(query, page) } returns expectedResult

        val result = useCase.invoke(query, page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getArtistsByQuery(query, page) }
    }

    @Test
    fun `should call repository getArtistsByQuery with empty query`() = runTest {
        val query = ""
        val expectedResult = emptyList<Artist>()
        coEvery { searchRepository.getArtistsByQuery(query, 1) } returns expectedResult

        val result = useCase.invoke(query)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getArtistsByQuery(query, 1) }
    }

    @Test
    fun `should call repository getArtistsByQuery with large page number`() = runTest {
        val query = "Adele"
        val page = 100
        val expectedResult = emptyList<Artist>()
        coEvery { searchRepository.getArtistsByQuery(query, page) } returns expectedResult

        val result = useCase.invoke(query, page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getArtistsByQuery(query, page) }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception when repository fails`() = runTest {
        val query = "Artist"
        coEvery {
            searchRepository.getArtistsByQuery(
                query,
                1
            )
        } throws RuntimeException("Search error")

        useCase.invoke(query)
    }

    @Test
    fun `should return repository result when successful`() = runTest {
        val query = "Taylor Swift"
        val page = 3
        val expectedResult = emptyList<Artist>()
        coEvery { searchRepository.getArtistsByQuery(query, page) } returns expectedResult

        val result = useCase.invoke(query, page)

        assertThat(result).isEqualTo(expectedResult)
        coVerify(exactly = 1) { searchRepository.getArtistsByQuery(query, page) }
    }
}