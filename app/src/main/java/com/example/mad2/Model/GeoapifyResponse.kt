package com.example.travelapp.network

data class GeoapifyResponse(
    val results: List<Place>
)

data class Place(
    val formatted: String
)
