package com.madrid.presentation.viewModel.detailsViewModel.actor

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.usecase.artist.GetArtistDetailsUseCase
import com.madrid.domain.usecase.artist.GetArtistMoviesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.detailsViewModel.actor.NetworkDetailsUiState
import com.madrid.presentation.viewModel.shared.parser.formatDateOfBirth
import com.madrid.presentation.viewModel.shared.parser.formatRate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val getArtistDetailsUseCase: GetArtistDetailsUseCase,
    private val getArtistMoviesUseCase: GetArtistMoviesUseCase,
    saveStateHandle: SavedStateHandle
) : BaseViewModel<NetworkDetailsUiState, Nothing>(
    NetworkDetailsUiState()
) {
    val args = saveStateHandle.toRoute<Destinations.ActorDetails>()

    init {
        loadActorDetails()
    }

    fun retryLoadData() {
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
                val mappedActor = actor.let {
                    NetworkDetailsUiState.CastUiState(
                        actorImageUrl = actor.imageUrl,
                        actorName = actor.name,
                        actorRole = actor.role,
                        dateOfBirth = formatDateOfBirth(actor.dateOfBirth),
                        location = actor.country,
                        id = actor.id.toString(),
                        description = actor.overview,
                        knownFor = knownForList.map { known ->
                            NetworkDetailsUiState.KnownMovieUiState(
                                title = known.title,
                                imageUrl = known.imageUrl,
                                rating = formatRate(known.rate),
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