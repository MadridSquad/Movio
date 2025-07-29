package com.madrid.movio.di

import com.madrid.data.dataSource.local.LocalDataSourceImpl
import com.madrid.data.dataSource.local.database.MovioDatabase
import com.madrid.data.repositories.ArtistRepositoryImpl
import com.madrid.data.repositories.MovieRepositoryImpl
import com.madrid.data.repositories.SearchRepositoryImpl
import com.madrid.data.repositories.SeriesRepositoryImpl
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.detectImageContent.GetImageBitmap
import com.madrid.detectImageContent.SensitiveContentDetection
import com.madrid.domain.repository.ArtistRepository
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    single { MovioDatabase.getInstance(androidContext()) }
    single { get<MovioDatabase>().movieDao() }
    single { get<MovioDatabase>().seriesDao() }
    single { get<MovioDatabase>().artistDao() }
    single { get<MovioDatabase>().movieGenreDao() }
    single { get<MovioDatabase>().seriesGenreDao() }
    single { get<MovioDatabase>().recentSearchDao() }
    single<LocalDataSource> { LocalDataSourceImpl(get(), get(), get(), get(), get(), get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get(), get()) }
    single { GetImageBitmap(get()) }
    single { SensitiveContentDetection(get()) }
    single<ArtistRepository> { ArtistRepositoryImpl(get()) }
}