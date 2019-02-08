package com.mvatech.ftrujillo.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.mvatech.ftrujillo.forecast.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)