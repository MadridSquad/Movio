package com.madrid.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {

    @Serializable
    data object SplashScreen : Destinations

    @Serializable
    data object SeeAllForYouScreen : Destinations

    @Serializable
    data object OnBoarding : Destinations

    @Serializable
    data object AuthenticationScreen : Destinations

    @Serializable
    data object HomeScreen : Destinations

    @Serializable
    data object LibraryScreen : Destinations

    @Serializable
    data object MoreScreen : Destinations

    @Serializable
    data object SearchScreen : Destinations

    @Serializable
    data class MovieDetailsScreen(
        val movieId: Int,
    ) : Destinations

    @Serializable
    data class SeriesDetailsScreen(
        val seriesId: Int,
        val seasonNumber: Int
    ) : Destinations

    @Serializable
    data class TopCastScreen(
        val mediaId: Int,
        val isMovie: Boolean
    ) : Destinations

    @Serializable
    data class ReviewsScreen(
        val mediaId: Int,
        val isMovie: Boolean
    ) : Destinations

    @Serializable
    data class SimilarMediaScreen(
        val mediaId: Int,
        val isMovie: Boolean
    ) : Destinations

    @Serializable
    data class SeasonsScreen(
        val seriesId: Int,
        val seasonNumber: Int
    ) : Destinations

    @Serializable
    data class EpisodesScreen(
        val seriesId: Int,
        val seasonNumber: Int
    ) : Destinations


    @Serializable
    data class ActorDetails(
        val artistId: Int,
    ) : Destinations

    @Serializable
    data class TopCast(
        val mediaId: Int,
        val isMovie: Boolean
    ) : Destinations

}