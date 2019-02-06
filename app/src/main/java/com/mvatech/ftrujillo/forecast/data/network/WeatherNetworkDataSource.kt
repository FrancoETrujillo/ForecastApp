package com.mvatech.ftrujillo.forecast.data.network

import androidx.lifecycle.LiveData
import com.mvatech.ftrujillo.forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}