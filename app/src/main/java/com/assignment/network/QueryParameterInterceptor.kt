package com.assignment.network

import okhttp3.Interceptor
import okhttp3.Response

class QueryParameterInterceptor constructor(
    private val appId: String,
    private val units: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("appid",appId)
            .addQueryParameter("units",units)
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
