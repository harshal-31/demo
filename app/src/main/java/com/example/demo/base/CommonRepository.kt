package com.example.demo.base

import androidx.lifecycle.MutableLiveData
import com.example.demo.base.data.LocationSchema
import com.example.demo.base.data.Restaurants

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */

 abstract class CommonRepository {
    val locationData = MutableLiveData<LocationSchema>()
    val restaurantData = MutableLiveData<Restaurants>()
 }