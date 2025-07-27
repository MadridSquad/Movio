package com.madrid.movio.di

import com.madrid.data.dataSource.local.LocalDataSourceImpl
import com.madrid.data.dataSource.local.MovioDatabase
import com.madrid.data.repositories.AllTrendingRepositoryImpl
import com.madrid.data.repositories.ArtistDetailsRepositoryImpl
import com.madrid.data.repositories.HomeRepositoryImpl
import com.madrid.data.repositories.MovieDetailsRepositoryImpl
import com.madrid.data.repositories.RecommendedRepositoryImp
import com.madrid.data.repositories.SearchRepositoryImpl
import com.madrid.data.repositories.SeriesDetailsRepositoryImpl
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.detectImageContent.GetImageBitmap
import com.madrid.detectImageContent.SensitiveContentDetection
import com.madrid.domain.repository.AllTrendingRepository
import com.madrid.domain.repository.ArtistDetailsRepository
import com.madrid.domain.repository.HomeRepository
import com.madrid.domain.repository.MovieDetailsRepository
import com.madrid.domain.repository.RecommendedRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesDetailsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    // region Search
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    // endregion

    // region Database & DAOs
    single { MovioDatabase.getInstance(androidContext()) }
    single { get<MovioDatabase>().movieDao() }
    single { get<MovioDatabase>().seriesDao() }
    single { get<MovioDatabase>().artistDao() }
    single { get<MovioDatabase>().movieGenreDao() }
    single { get<MovioDatabase>().seriesGenreDao() }
    single { get<MovioDatabase>().recentSearchDao() }
    // endregion

    // region Local Data Source
    single<LocalDataSource> { LocalDataSourceImpl(get(), get(), get(), get(), get(), get()) }
    // endregion

    // region Recommended
    single<RecommendedRepository> { RecommendedRepositoryImp(get(), get()) }
    // endregion

    // region Movie Details
    single<MovieDetailsRepository> { MovieDetailsRepositoryImpl(get(), get()) }
    // endregion

    // region Series Details
    single<SeriesDetailsRepository> { SeriesDetailsRepositoryImpl(get(), get()) }
    // endregion

    // region Home
    single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    // endregion

    // region Image Detection
    single { GetImageBitmap(get()) }
    single { SensitiveContentDetection(get()) }
    // endregion

    // region Trending
    single<AllTrendingRepository> { AllTrendingRepositoryImpl(get()) }
    // endregion

    // region Artist Details
    single<ArtistDetailsRepository> { ArtistDetailsRepositoryImpl(get()) }
    // endregion
}