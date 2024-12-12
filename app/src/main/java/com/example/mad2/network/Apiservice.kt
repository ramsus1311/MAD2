package com.example.mad2.network

import com.example.mad2.Dataclasses.Places
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("places")
    fun searchCities(
        @Query("categories") categories: String = "commercial.supermarket",
        @Query("filter") filter: String = "rect%3A10.716463143326969%2C48.755151258420966%2C10.835314015356737%2C48.680903341613316",
        @Query("limit") limit: Int = 20,
        @Query("apiKey") apiKey: String = "0842f86d8840420ea9a22b8cbd119c62" // Use your API Key
    ): Call<Places>
}
