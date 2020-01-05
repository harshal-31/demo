package com.example.demo.base

import android.content.Context
import com.example.demo.base.data.LocationSchema
import com.example.demo.base.data.Restaurants
import retrofit2.Call

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */


class AppDataManager(val context: Context, val apiHelper: ApiHelper) : DataManager {
    override fun getRestaurants(lat: Double, lon: Double): Call<Restaurants> = apiHelper.getRestaurants(lat, lon)
    override fun getLocations(query: String, count: Int): Call<LocationSchema> = apiHelper.getLocations(query, count)

}