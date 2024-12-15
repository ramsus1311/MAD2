package com.example.travelapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyService {

    @GET("v1/geocode/search")
    fun getCitiesFromName(
        @Query("text") cityName: String,
        @Query("apiKey") apiKey: String
    ): Call<GeoapifyGeocodeResponse>

    @GET("v2/places")
    fun searchPlace(
        @Query("categories") category: String,
        @Query("filter") filter: String,
        @Query("limit") limit: Int = 20,
        @Query("apiKey") apiKey: String,
        @Query("offset") offset: Int = 0
    ): Call<GeoapifyResponse>
}
