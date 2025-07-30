package com.madrid.presentation.viewModel.detailsViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.artist.GetArtistDetailsUseCase
import com.madrid.domain.usecase.artist.GetArtistMoviesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class ActorDetailsViewModel(
    private val getArtistDetailsUseCase: GetArtistDetailsUseCase,
    private val getArtistMoviesUseCase: GetArtistMoviesUseCase,
    private val saveStateHandle: SavedStateHandle
) : BaseViewModel<MovieDetailsUiState, Nothing>(
    MovieDetailsUiState()
) {
    val args = saveStateHandle.toRoute<Destinations.ActorDetails>()

    init {
        loadActorDetails()
    }

    private fun loadActorDetails() {
        tryToExecute(
            function = {
                val actor = getArtistDetailsUseCase(args.artistId)
                val knownForList = getArtistMoviesUseCase(args.artistId)
                Pair(actor, knownForList)
            },
            onSuccess = { (actor, knownForList) ->
                val mappedActor = actor?.let {
                    MovieDetailsUiState.CastUiState(
                        actorImageUrl = actor.imageUrl,
                        actorName = actor.name,
                        actorRole = actor.role,
                        dateOfBirth = actor.dateOfBirth,
                        location = actor.country,
                        id = actor.id.toString(),
                        description = actor.overview,
                        knownFor = knownForList.map { known ->
                            MovieDetailsUiState.KnownMovieUiState(
                                title = known.title,
                                imageUrl = known.imageUrl,
                                rating = known.rate.toString(),
                                mediaId = known.id,
                            )
                        }
                    )
                }
                updateState {
                    it.copy(
                        selectedActor = mappedActor,
                        isLoading = false,
                        errorMessage = null,
//                        movieId = args
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error"
                    )
                }
            }
        )
    }
}