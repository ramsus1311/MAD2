package com.example.mad2.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private const val BASE_URL = "https://api.geoapify.com/" // Geoapify API base URL

    val retrofit: Retrofit by lazy {
        // Create a logging interceptor to log API request and response
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        // Build the OkHttpClient with the logging interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(logging) // Add the logging interceptor
            .build()

        // Build the Retrofit instance with the OkHttpClient and Gson converter
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL for the API
            .client(client) // Add the OkHttpClient to Retrofit
            .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
            .build()
    }
}
