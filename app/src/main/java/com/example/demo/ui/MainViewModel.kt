package com.example.demo.ui

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.demo.base.BaseViewModel
import com.example.demo.base.data.LocationSuggestion
import com.example.demo.base.data.NearbyRestaurant
import com.example.demo.base.dslPropertyChangeCallback

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    val serachData = ObservableField<String>()

    init {
        handleListener()
    }

    private fun handleListener() {
        this.serachData.dslPropertyChangeCallback {
            getLocation(it, 10)
        }
    }

    val locItemClick =  MutableLiveData<LocationSuggestion>()

    fun getLocation(query: String, count: Int) = repository.getLocation(query, count)

    fun callRestaurantsNearby(lat: Double, lon: Double) = repository.getRestaurantsNearBy(lat, lon)

    val getNearbyRestaurant = repository.restaurantData

    val locData = repository.locationData

    val itemClick = MutableLiveData<Pair<NearbyRestaurant, Int>>()


}
