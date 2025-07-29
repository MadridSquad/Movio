package com.madrid.domain.usecase.artist

import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.ArtistRepository

class GetArtistDetailsUseCase(private val artistRepository: ArtistRepository) {
    suspend operator fun invoke(artistId: Int): Artist =
        artistRepository.getArtistDetailsById(artistId)
}