package com.example.demo.base

import com.example.demo.base.data.LocationSchema
import com.example.demo.base.data.Restaurants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */


 interface ApiInterface {
    @GET("geocode")
    fun getRestaurants(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<Restaurants>

    @GET("locations")
    fun getLocations(@Query("query") query: String, @Query("count") count: Int): Call<LocationSchema>
}