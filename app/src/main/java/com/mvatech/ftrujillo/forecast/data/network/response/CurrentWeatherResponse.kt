package com.mvatech.ftrujillo.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.mvatech.ftrujillo.forecast.data.db.entity.CurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation


)