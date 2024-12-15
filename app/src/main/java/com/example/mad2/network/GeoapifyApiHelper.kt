package com.example.mad2.network
import android.util.Log
import com.example.mad2.model.CityDetails
import com.example.mad2.model.PlaceOfInterest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class GeoapifyApiHelper(private val apiKey: String) {

    private val client = OkHttpClient()

    private val BASE_URL = "https://api.geoapify.com/"

    suspend fun searchForCityByName(cityName: String): List<CityDetails>? {
        return withContext(Dispatchers.IO) {
            try {
                val limit = 20
                val url = BASE_URL + "v1/geocode/search?text=$cityName&lang=en&limit=$limit&type=city&apiKey=$apiKey"

                val request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val cityDetails = mutableListOf<CityDetails>()

                if (!response.isSuccessful || responseBody == null) {
                    Log.e("ERROR: ", "${response.code} - ${response.message}")
                    return@withContext null
                }

                val json = JSONObject(responseBody)
                val features = json.getJSONArray("features")


                for(i in 0 until features.length()) {
                    val feature = features.getJSONObject(i)
                    val properties = feature.getJSONObject("properties")
                    val city = properties.optString("city", "" )
                    val state = properties.optString("state", "" )
                    val country = properties.optString("country", "" )
                    val placeId = properties.optString("place_id", "" )
                    cityDetails.add(CityDetails(city, country, state, placeId))
                }

                return@withContext cityDetails
            }
            catch (e: Exception) {
                println("Exception: ${e.localizedMessage}")
                return@withContext null
            }
        }
    }

    suspend fun getPlacesOfInterestForCity(placeId: String, category: String): List<PlaceOfInterest>? {
        return withContext(Dispatchers.IO) {
            try {
                val limit = 20
                val url = BASE_URL + "v2/places?filter=place:$placeId&categories=$category&limit=$limit&apiKey=$apiKey"

                val request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val placesOfInterest = mutableListOf<PlaceOfInterest>()

                if (!response.isSuccessful || responseBody == null) {
                    Log.e("ERROR: ", "${response.code} - ${response.message}")
                    return@withContext null
                }

                val json = JSONObject(responseBody)
                val features = json.getJSONArray("features")

                for(i in 0 until features.length()) {
                    val feature = features.getJSONObject(i)
                    val properties = feature.getJSONObject("properties")
                    val name = properties.optString("name", "N/A" )
                    val city = properties.optString("city", "N/A" )
                    val country = properties.optString("country", "N/A" )
                    val state = properties.optString("state", "N/A" )
                    val houseNumber = properties.optString("housenumber", "N/A")
                    val street = properties.optString("street", "N/A")
                    val postcode = properties.optString("postcode", "N/A")
                    val placeId = properties.optString("place_id", "N/A" )

                    placesOfInterest.add(PlaceOfInterest(name, city, country, state, houseNumber, street, postcode, placeId))
                }

                return@withContext placesOfInterest

            }
            catch (e: Exception) {
                println("Exception: ${e.localizedMessage}")
                return@withContext null
            }
        }
    }

}