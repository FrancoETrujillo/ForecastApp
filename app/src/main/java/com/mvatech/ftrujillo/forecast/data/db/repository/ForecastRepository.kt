package com.mvatech.ftrujillo.forecast.data.db.repository

import androidx.lifecycle.LiveData
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}