package com.madrid.presentation.navigation

import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import com.madrid.presentation.viewModel.seeAll.movies.factory.SeeAllMoviesType
import com.madrid.presentation.viewModel.seeAll.tvShows.factory.SeeAllTvShowType
import kotlinx.serialization.Serializable

sealed interface Destinations {

    @Serializable
    data object SplashScreen : Destinations

    @Serializable
    data object SeeAllForYouScreen : Destinations

    @Serializable
    data object OnBoarding : Destinations

    @Serializable
    data object LoginScreen : Destinations

    @Serializable
    data object HomeScreen : Destinations

    @Serializable
    data object LibraryScreen : Destinations

    @Serializable
    data object MoreScreen : Destinations

    @Serializable
    data object SearchScreen : Destinations

    @Serializable
    data object WatchListViewAllScreen : Destinations

    @Serializable
    data class SeeAllTvShowsScreen(
        val type: SeeAllTvShowType
    ) : Destinations

    @Serializable
    data class SeeAllMoviesScreen(
        val type: SeeAllMoviesType
    ) : Destinations

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
    data class TopCast(
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
    data class WebViewScreen(
        val url: String
    ) : Destinations

    @Serializable
    data class ForgotPassword(val url: String): Destinations

    @Serializable
    data class ActorDetails(
        val artistId: Int,
    ) : Destinations
    @Serializable
    data object MyRatingScreen : Destinations

    @Serializable
    data class ViewAllScreen(
        val type: ViewAllType
    ) : Destinations

    @Serializable
    data class WatchListDetailsScreen(
        val watchListId: Int,
        val watchListTitle: String,
    ) : Destinations

}
