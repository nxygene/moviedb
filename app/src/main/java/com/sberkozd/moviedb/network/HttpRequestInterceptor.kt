package com.sberkozd.moviedb.network

import android.content.Context
import com.sberkozd.moviedb.BuildConfig
import com.sberkozd.moviedb.util.isConnected
import okhttp3.Interceptor
import okhttp3.Response


internal class HttpRequestInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)

        val request = requestBuilder.build()


        return chain.proceed(request)
    }
}
