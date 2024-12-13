package com.example.travelapp.network

data class GeoapifyResponse(
    val results: List<Place>
)

data class Place(
    val formatted: String
)

data class GeoapifyGeocodeResponse(
    val results: List<GeoapifyGeocodeResult>
)

data class GeoapifyGeocodeResult(
    val place_id: String,  // The city ID (place_id)
    val formatted: String  // The formatted city name
)