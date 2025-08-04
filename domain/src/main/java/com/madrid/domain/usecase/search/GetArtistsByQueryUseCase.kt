package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository
import javax.inject.Inject

class GetArtistsByQueryUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(
        query: String, page: Int = 1
    ) = searchRepository.getArtistsByQuery(query, page)
}