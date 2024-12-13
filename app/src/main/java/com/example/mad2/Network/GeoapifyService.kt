package com.example.travelapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyService {
    @GET("v2/places")
    fun searchPlace(
        @Query("categories") category: String,
        @Query("filter") filter: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("apiKey") apiKey: String
    ): Call<GeoapifyResponse>
}
