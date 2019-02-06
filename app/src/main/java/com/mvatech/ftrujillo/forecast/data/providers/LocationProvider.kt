package com.mvatech.ftrujillo.forecast.data.providers

import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation):Boolean
    suspend fun getPreferredLocationString():String
}