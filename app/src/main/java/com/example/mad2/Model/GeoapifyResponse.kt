package com.example.travelapp.network

data class GeoapifyResponse(
    val features: List<GeoapifyGeocodeResult>
)

data class Place(
    val formatted: String,   // Full address (e.g., "New York, Krunska, 11000 Belgrade, Serbia")
    val place_id: String,    // Unique place identifier (ID)
)

data class GeoapifyGeocodeResponse(
    val features: List<GeoapifyGeocodeResult>
)

data class GeoapifyGeocodeResult(
    val properties: Properties
)

data class Properties(
    val formatted: String,   // Full address (e.g., "New York, Krunska, 11000 Belgrade, Serbia")
    val place_id: String,
)