package com.mvatech.ftrujillo.forecast.data

import com.google.gson.annotations.SerializedName
import com.mvatech.ftrujillo.forecast.data.db.entity.CurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.entity.Location

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)