package com.madrid.presentation.viewModel.detailsViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.mediaDeatailsUseCase.ArtistDetailsUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel

class ActorDetailsViewModel(
    private val actorDetailsUseCase: ArtistDetailsUseCase,
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
                val actor = actorDetailsUseCase.getArtistDetailsById(args.artistId)
                val knownForList = actorDetailsUseCase.getArtistKnownForById(args.artistId)
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
                        description = actor.description,
                        knownFor = knownForList.map { known ->
                            MovieDetailsUiState.KnownMovieUiState(
                                title = known.title,
                                imageUrl = known.imageUrl,
                                rating = known.voteAverage.toString(),
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
//                        movieId = args.
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