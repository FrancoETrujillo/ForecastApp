package com.mvatech.ftrujillo.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntry: ForecastDaysContainer,
    val location: WeatherLocation
)