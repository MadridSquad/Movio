package com.madrid.movio.di

import com.madrid.data.BuildConfig.BASE_URL
import com.madrid.data.dataSource.remote.MovieInterceptor
import com.madrid.data.dataSource.remote.MovioApi
import com.madrid.data.dataSource.remote.RemoteDataSourceImpl
import com.madrid.data.repositories.remote.RemoteDataSource
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val remoteModule = module {

    single<Interceptor> { MovieInterceptor() }

    single { OkHttpClient.Builder().addInterceptor(get<Interceptor>()).build() }

    single<String>(named("baseUrl")) {
        val apiVersion = "/3/"
        val urlProtocol = "https://"
        urlProtocol + BASE_URL + apiVersion
    }

    single {
        val retrofit :Retrofit = Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MovioApi::class.java)
    }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single { Json { ignoreUnknownKeys = true } }
}