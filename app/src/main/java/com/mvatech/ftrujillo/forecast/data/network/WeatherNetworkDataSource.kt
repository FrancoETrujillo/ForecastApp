package com.mvatech.ftrujillo.forecast.data.network

import androidx.lifecycle.LiveData
import com.mvatech.ftrujillo.forecast.data.network.response.CurrentWeatherResponse
import com.mvatech.ftrujillo.forecast.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        days: Int,
        languageCode: String
    )
}