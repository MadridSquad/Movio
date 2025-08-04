package com.madrid.domain.usecase.artist

import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.ArtistRepository
import javax.inject.Inject

class GetArtistDetailsUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend operator fun invoke(artistId: Int): Artist =
        artistRepository.getArtistDetailsById(artistId)
}