package com.example.demo.base

import com.example.demo.base.data.LocationSchema
import com.example.demo.base.data.Restaurants
import retrofit2.Call

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */


interface ApiHelper {

    fun getRestaurants(lat: Double, lon: Double): Call<Restaurants>

    fun getLocations(query: String, count: Int): Call<LocationSchema>

}