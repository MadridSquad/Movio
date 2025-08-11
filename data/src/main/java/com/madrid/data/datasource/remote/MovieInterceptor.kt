package com.madrid.data.datasource.remote

import com.madrid.data.BuildConfig.API_KEY
import com.madrid.data.datasource.remote.utils.Constants.KEY
import com.madrid.data.datasource.remote.utils.responseWrapper
import okhttp3.Interceptor
import okhttp3.Response

class MovieInterceptor(

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url
            .newBuilder()
            .addQueryParameter(KEY, API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .header("accept", "application/json")
            .url(url)
            .build()

        val response : Response = chain.proceed(newRequest)

        return responseWrapper(response)
    }

}