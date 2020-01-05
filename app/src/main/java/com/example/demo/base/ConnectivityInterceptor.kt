package com.example.demo.base

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Harshal Chaudhari on 20/12/19.
 */

class ConnectivityInterceptor(val app: Application) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val builder = chain.request().newBuilder()
            val maxStale = 60

            builder.removeHeader("Pragma")
                .addHeader("user-key", "415582d3ab3787237b64a4bd67eab33c")
                .header("Cache-Control", "public, max-age=$maxStale")
                .build()

            return chain.proceed(builder.build())
        } catch (e: IOException) {
            throw IOException("No Connectivity Exception")
        }
    }

}