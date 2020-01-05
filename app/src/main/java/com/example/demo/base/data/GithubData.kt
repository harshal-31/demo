package com.example.demo.base.data
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

import com.google.gson.annotations.SerializedName



/**
 * Created by Harshal Chaudhari on 18/12/19.
 */

@Parcelize
data class LocationSchema(
    @SerializedName("has_more")
    val hasMore: Int? = 0, // 0
    @SerializedName("has_total")
    val hasTotal: Int? = 0, // 0
    @SerializedName("location_suggestions")
    val locationSuggestions: List<LocationSuggestion> = listOf(),
    @SerializedName("status")
    val status: String? = "", // success
    @SerializedName("user_has_addresses")
    val userHasAddresses: Boolean? = false // true
) : Parcelable

@Parcelize
data class LocationSuggestion(
    @SerializedName("city_id")
    val cityId: Int? = 0, // 904
    @SerializedName("city_name")
    val cityName: String? = "", // Piedmont Triad
    @SerializedName("country_id")
    val countryId: Int? = 0, // 216
    @SerializedName("country_name")
    val countryName: String? = "", // United States
    @SerializedName("entity_id")
    val entityId: Int? = 0, // 133771
    @SerializedName("entity_type")
    val entityType: String? = "", // subzone
    @SerializedName("latitude")
    val latitude: Double? = 0.0, // 36.494928406
    @SerializedName("longitude")
    val longitude: Double? = 0.0, // -80.6157541932
    @SerializedName("title")
    val title: String? = "" // Mount Airy, Piedmont Triad
) : Parcelable








