package com.madrid.movio.di.hilt

import com.madrid.data.BuildConfig.BASE_URL
import com.madrid.data.dataSource.remote.MovieInterceptor
import com.madrid.data.dataSource.remote.MovioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(URL_PROTOCOL + BASE_URL + API_VERSION)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    const val API_VERSION = "/3/"
    const val URL_PROTOCOL = "https://"

    @Provides
    @Singleton
    fun provideMovieInterceptor(): Interceptor {
        return MovieInterceptor()
    }

    @Provides
    @Singleton
    fun provideMovioApi(retrofit: Retrofit): MovioApi = retrofit.create(MovioApi::class.java)
}