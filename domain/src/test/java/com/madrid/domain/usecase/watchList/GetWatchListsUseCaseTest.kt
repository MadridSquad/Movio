package com.madrid.domain.usecase.watchList

import com.google.common.truth.Truth
import com.madrid.domain.entity.WatchList
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.ListRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("UnusedFlow")
class GetWatchListsUseCaseTest {
    private val authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private val listRepository: ListRepository = mockk(relaxed = true)
    private lateinit var useCase: GetWatchListsUseCase

    @Before
    fun setUp() {
        useCase = GetWatchListsUseCase(authenticationRepository, listRepository)
    }

    @Test
    fun `invoke SHOULD return watch lists when repository succeeds`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { listRepository.getLists("test_session_id") } returns testWatchLists

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testWatchLists)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getLists("test_session_id") }
    }

    @Test
    fun `invoke SHOULD return empty list when repository returns empty list`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { listRepository.getLists("test_session_id") } returns emptyList()

        val result = useCase.invoke()

        Truth.assertThat(result).isEmpty()
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getLists("test_session_id") }
    }

    @Test
    fun `invoke SHOULD use custom session id from authentication repository`() = runTest {
        val customSessionId = "custom_session_789"
        coEvery { authenticationRepository.getSessionId() } returns flowOf(customSessionId)
        coEvery { listRepository.getLists(customSessionId) } returns testWatchLists

        val result = useCase.invoke()

        Truth.assertThat(result).isEqualTo(testWatchLists)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getLists(customSessionId) }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when authentication repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } throws RuntimeException("Authentication error")

        useCase.invoke()
    }

    @Test(expected = RuntimeException::class)
    fun `invoke SHOULD throw exception when list repository fails`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { listRepository.getLists("test_session_id") } throws RuntimeException("List error")

        useCase.invoke()
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke SHOULD throw exception when session id is empty`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("")
        coEvery { listRepository.getLists("") } throws IllegalStateException("Empty session ID")

        useCase.invoke()
    }

    @Test
    fun `invoke SHOULD call repositories in correct order`() = runTest {
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { listRepository.getLists("test_session_id") } returns testWatchLists

        useCase.invoke()

        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getLists("test_session_id") }
    }

    @Test
    fun `invoke SHOULD return single watch list when repository returns one item`() = runTest {
        val singleWatchList = listOf(testWatchLists.first())
        coEvery { authenticationRepository.getSessionId() } returns flowOf("test_session_id")
        coEvery { listRepository.getLists("test_session_id") } returns singleWatchList

        val result = useCase.invoke()

        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result).isEqualTo(singleWatchList)
        coVerify(exactly = 1) { authenticationRepository.getSessionId() }
        coVerify(exactly = 1) { listRepository.getLists("test_session_id") }
    }

    private companion object {
        val testWatchLists = listOf(
            WatchList(
                id = 1,
                name = "My Favorites",
                description = "My favorite movies and shows",
                itemCount = 10,
                posterUrl = "/poster1.jpg"
            ),
            WatchList(
                id = 2,
                name = "Watch Later",
                description = "Items to watch later",
                itemCount = 5,
                posterUrl = "/poster2.jpg"
            ),
            WatchList(
                id = 3,
                name = "Completed",
                description = "Already watched",
                itemCount = 25,
                posterUrl = "/poster3.jpg"
            )
        )
    }
}