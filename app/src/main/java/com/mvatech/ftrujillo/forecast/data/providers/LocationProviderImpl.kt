package com.mvatech.ftrujillo.forecast.data.providers

import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Columbus"
    }
}