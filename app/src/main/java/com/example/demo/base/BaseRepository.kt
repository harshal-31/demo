package com.example.demo.base

import android.app.Application
import com.example.demo.base.RetroResponse.*


/**
 * Created by Harshal Chaudhari on 18/12/19.
 */

class BaseRepository(application: Application): CommonRepository() {
    private val dataManager: DataManager
    private lateinit var errorCallback: BaseErrorCallBack


    fun setErrorCallback(errorCallBacks: BaseErrorCallBack) {
        this.errorCallback = errorCallBacks
    }

    init {
        dataManager = AppDataManager(application.applicationContext, ApiManager(application))
    }


    fun getRestaurantsNearBy(lat: Double, lon: Double) {
        retroCall(dataManager.getRestaurants(lat, lon)) {
            when (it) {
                is Success -> restaurantData.value = it.data
                is Failure -> restaurantData.value = null
                is NullData -> restaurantData.value = null
                is OnNetworkChange -> errorCallback.onNetworkChanged(it.message)
            }
        }
    }


    fun getLocation(query: String, count: Int) {
        retroCall(dataManager.getLocations(query, count)) {
            when (it) {
                is Success -> locationData.value = it.data
                is Failure -> locationData.value = null
                is NullData -> locationData.value = null
                is OnNetworkChange -> errorCallback.onNetworkChanged(it.message)
            }
        }
    }




}

interface BaseErrorCallBack {
    fun onNetworkChanged(message: String)
}