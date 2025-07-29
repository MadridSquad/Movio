package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository

class GetArtistsByQueryUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(
        query: String, page: Int = 1
    ) = searchRepository.getArtistsByQuery(query, page)
}