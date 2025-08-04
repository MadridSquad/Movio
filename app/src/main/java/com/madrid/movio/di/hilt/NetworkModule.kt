package com.madrid.movio.di.hilt

import android.content.Context
import androidx.work.WorkerParameters
import com.madrid.data.dataSource.remote.MovieInterceptor
import com.madrid.data.dataSource.remote.MovioApi
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import com.madrid.presentation.screens.searchScreen.RecentSearchSyncWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val API_VERSION = "/3/"
const val URL_PROTOCOL = "https://"
const val BASE_URL = "api.themoviedb.org"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(movieInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(movieInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(URL_PROTOCOL + BASE_URL + API_VERSION)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieInterceptor(): Interceptor {
        return MovieInterceptor()
    }

    @Provides
    @Singleton
    fun provideMovioApi(retrofit: Retrofit): MovioApi = retrofit.create(MovioApi::class.java)

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideWorker(
        context: Context,
        worker: WorkerParameters,
        useCase: ClearAllRecentSearchesUseCase
    ): RecentSearchSyncWorker {
        return RecentSearchSyncWorker(context, worker, useCase)
    }
}