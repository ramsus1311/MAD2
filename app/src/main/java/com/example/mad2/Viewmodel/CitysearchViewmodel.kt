package com.example.mad2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mad2.network.ApiClient
import com.example.mad2.network.ApiService
import com.example.mad2.Dataclasses.Places
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitysearchViewModel : ViewModel() {

    private val _places = mutableStateOf<List<Places.Feature>?>(null)
    val places: State<List<Places.Feature>?> = _places

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun searchCities(query: String) {
        _isLoading.value = true
        val apiService = ApiClient.createService(ApiService::class.java)
        apiService.searchCities(query).enqueue(object : Callback<Places> {
            override fun onResponse(call: Call<Places>, response: Response<Places>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _places.value = response.body()?.features
                } else {
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<Places>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }
}
