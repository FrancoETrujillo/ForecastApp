package com.mvatech.ftrujillo.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepository
import com.mvatech.ftrujillo.forecast.internal.UnitSystem
import com.mvatech.ftrujillo.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred() {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
