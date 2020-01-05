package com.example.demo.base

import android.app.Application
import com.example.demo.base.data.LocationSchema
import com.example.demo.base.data.Restaurants
import com.google.gson.GsonBuilder
import com.ncornette.cache.OkCacheControl
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */

class ApiManager(application: Application) : ApiHelper {

    private val okHttpClient = OkHttpClient.Builder()
    private val gson = GsonBuilder().setPrettyPrinting().setLenient().serializeNulls().create()!!
    private var okCache: OkHttpClient

    init {
        val httpCacheDirectory = File(application.applicationContext.cacheDir, "responses")
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cache = Cache(httpCacheDirectory, cacheSize)

        okHttpClient.connectTimeout(30_000, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(30_000, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(30_000, TimeUnit.MILLISECONDS)
        okHttpClient.addNetworkInterceptor(ConnectivityInterceptor(application))

        okCache = OkCacheControl.on(okHttpClient)
            .overrideServerCachePolicy(1, TimeUnit.MINUTES)
            .forceCacheWhenOffline(NetworkUtil.okCacheControl(application))
            .apply()
            .cache(cache)
            .build()
    }

    private fun getApiInterface() = Retrofit.Builder()
        .baseUrl(Constants.DEV_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okCache)
        .build()
        .create(ApiInterface::class.java)


    override fun getRestaurants(lat: Double, lon: Double): Call<Restaurants> = getApiInterface().getRestaurants(lat, lon)
    override fun getLocations(query: String, count: Int): Call<LocationSchema> = getApiInterface().getLocations(query, count)
}








