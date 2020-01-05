package com.example.demo.base.data
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

import com.google.gson.annotations.SerializedName


/**
 * Created by Harshal Chaudhari on 19/12/19.
 */



@Parcelize
data class Restaurants(
    @SerializedName("link")
    val link: String? = "", // https://www.zomato.com/mumbai/airoli-restaurants
    @SerializedName("location")
    val location: Location? = Location(),
    @SerializedName("nearby_restaurants")
    val nearbyRestaurants: List<NearbyRestaurant> = listOf(),
    @SerializedName("popularity")
    val popularity: Popularity? = Popularity()
) : Parcelable

@Parcelize
data class Location(
    @SerializedName("city_id")
    val cityId: Int? = 0, // 3
    @SerializedName("city_name")
    val cityName: String? = "", // Mumbai
    @SerializedName("country_id")
    val countryId: Int? = 0, // 1
    @SerializedName("country_name")
    val countryName: String? = "", // India
    @SerializedName("entity_id")
    val entityId: Int? = 0, // 2306
    @SerializedName("entity_type")
    val entityType: String? = "", // subzone
    @SerializedName("latitude")
    val latitude: String? = "", // 19.1585670000
    @SerializedName("longitude")
    val longitude: String? = "", // 72.9923740000
    @SerializedName("title")
    val title: String? = "" // Airoli
) : Parcelable

@Parcelize
data class NearbyRestaurant(
    @SerializedName("restaurant")
    val restaurant: Restaurant? = Restaurant()
) : Parcelable

@Parcelize
data class Restaurant(
    @SerializedName("apikey")
    val apikey: String? = "", // 415582d3ab3787237b64a4bd67eab33c
    @SerializedName("average_cost_for_two")
    val averageCostForTwo: Int? = 0, // 400
    @SerializedName("book_again_url")
    val bookAgainUrl: String? = "",
    @SerializedName("book_form_web_view_url")
    val bookFormWebViewUrl: String? = "",
    @SerializedName("cuisines")
    val cuisines: String? = "", // North Indian, Biryani
    @SerializedName("currency")
    val currency: String? = "", // Rs.
    @SerializedName("deeplink")
    val deeplink: String? = "", // zomato://restaurant/18306487
    @SerializedName("events_url")
    val eventsUrl: String? = "", // https://www.zomato.com/mumbai/bismillah-catering-airoli-navi-mumbai/events#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1
    @SerializedName("featured_image")
    val featuredImage: String? = "", // https://b.zmtcdn.com/data/pictures/6/18397676/eaf5cfa8d9393127f9f1355a8618472a.jpg?output-format=webp
    @SerializedName("has_online_delivery")
    val hasOnlineDelivery: Int? = 0, // 1
    @SerializedName("has_table_booking")
    val hasTableBooking: Int? = 0, // 0
    @SerializedName("id")
    val id: String? = "", // 18306487
    @SerializedName("include_bogo_offers")
    val includeBogoOffers: Boolean? = false, // true
    @SerializedName("is_book_form_web_view")
    val isBookFormWebView: Int? = 0, // 0
    @SerializedName("is_delivering_now")
    val isDeliveringNow: Int? = 0, // 0
    @SerializedName("is_table_reservation_supported")
    val isTableReservationSupported: Int? = 0, // 0
    @SerializedName("is_zomato_book_res")
    val isZomatoBookRes: Int? = 0, // 0
    @SerializedName("location")
    val location: LocationX? = LocationX(),
    @SerializedName("menu_url")
    val menuUrl: String? = "", // https://www.zomato.com/mumbai/bismillah-catering-airoli-navi-mumbai/menu?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1&openSwipeBox=menu&showMinimal=1#tabtop
    @SerializedName("mezzo_provider")
    val mezzoProvider: String? = "", // OTHER
    @SerializedName("name")
    val name: String? = "", // Bismillah Catering
    @SerializedName("offers")
    val offers: List<String> = listOf(),
    @SerializedName("opentable_support")
    val opentableSupport: Int? = 0, // 0
    @SerializedName("photos_url")
    val photosUrl: String? = "", // https://www.zomato.com/mumbai/bismillah-catering-airoli-navi-mumbai/photos?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1#tabtop
    @SerializedName("price_range")
    val priceRange: Int? = 0, // 1
    @SerializedName("R")
    val r: R? = R(),
    @SerializedName("switch_to_order_menu")
    val switchToOrderMenu: Int? = 0, // 0
    @SerializedName("thumb")
    val thumb: String? = "", // https://b.zmtcdn.com/data/pictures/6/18397676/eaf5cfa8d9393127f9f1355a8618472a.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A
    @SerializedName("url")
    val url: String? = "", // https://www.zomato.com/mumbai/bismillah-catering-airoli-navi-mumbai?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1
    @SerializedName("user_rating")
    val userRating: UserRating? = UserRating()
) : Parcelable

@Parcelize
data class LocationX(
    @SerializedName("address")
    val address: String? = "", // Shop 11&12, Mhalsa Heights, Plot 37,Sec 19, Airoli, Navi Mumbai
    @SerializedName("city")
    val city: String? = "", // Navi Mumbai
    @SerializedName("city_id")
    val cityId: Int? = 0, // 3
    @SerializedName("country_id")
    val countryId: Int? = 0, // 1
    @SerializedName("latitude")
    val latitude: String? = "", // 19.1608780001
    @SerializedName("locality")
    val locality: String? = "", // Airoli
    @SerializedName("locality_verbose")
    val localityVerbose: String? = "", // Airoli, Navi Mumbai
    @SerializedName("longitude")
    val longitude: String? = "", // 72.9903175682
    @SerializedName("zipcode")
    val zipcode: String? = ""
) : Parcelable

@Parcelize
data class R(
    @SerializedName("has_menu_status")
    val hasMenuStatus: HasMenuStatus? = HasMenuStatus(),
    @SerializedName("res_id")
    val resId: Int? = 0 // 18306487
) : Parcelable

@Parcelize
data class HasMenuStatus(
    @SerializedName("delivery")
    val delivery: Int? = 0, // -1
    @SerializedName("takeaway")
    val takeaway: Int? = 0 // -1
) : Parcelable

@Parcelize
data class UserRating(
    @SerializedName("aggregate_rating")
    val aggregateRating: String? = "", // 3.9
    @SerializedName("rating_color")
    val ratingColor: String? = "", // 9ACD32
    @SerializedName("rating_obj")
    val ratingObj: RatingObj? = RatingObj(),
    @SerializedName("rating_text")
    val ratingText: String? = "", // Good
    @SerializedName("votes")
    val votes: String? = "" // 573
) : Parcelable

@Parcelize
data class RatingObj(
    @SerializedName("bg_color")
    val bgColor: BgColor? = BgColor(),
    @SerializedName("title")
    val title: Title? = Title()
) : Parcelable

@Parcelize
data class BgColor(
    @SerializedName("tint")
    val tint: String? = "", // 600
    @SerializedName("type")
    val type: String? = "" // lime
) : Parcelable

@Parcelize
data class Title(
    @SerializedName("text")
    val text: String? = "" // 3.9
) : Parcelable

@Parcelize
data class Popularity(
    @SerializedName("city")
    val city: String? = "", // Mumbai
    @SerializedName("nearby_res")
    val nearbyRes: List<String?>? = listOf(),
    @SerializedName("nightlife_index")
    val nightlifeIndex: String? = "", // 2.46
    @SerializedName("nightlife_res")
    val nightlifeRes: String? = "", // 10
    @SerializedName("popularity")
    val popularity: String? = "", // 4.71
    @SerializedName("popularity_res")
    val popularityRes: String? = "", // 100
    @SerializedName("subzone")
    val subzone: String? = "", // Airoli
    @SerializedName("subzone_id")
    val subzoneId: Int? = 0, // 2306
    @SerializedName("top_cuisines")
    val topCuisines: List<String?>? = listOf()
) : Parcelable