package com.mvatech.ftrujillo.forecast.data.network.response

import androidx.lifecycle.LiveData
import com.mvatech.ftrujillo.forecast.data.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}