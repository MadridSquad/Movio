package com.madrid.domain.usecase.artist

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.ArtistRepository
import javax.inject.Inject

class GetArtistMoviesUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(artistId: Int): List<Movie> =
        artistRepository.getArtistMovies(artistId)
}