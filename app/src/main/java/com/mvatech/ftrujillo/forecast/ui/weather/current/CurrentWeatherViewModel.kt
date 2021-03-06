package com.mvatech.ftrujillo.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepository
import com.mvatech.ftrujillo.forecast.data.providers.UnitProvider
import com.mvatech.ftrujillo.forecast.internal.UnitSystem
import com.mvatech.ftrujillo.forecast.internal.lazyDeferred
import com.mvatech.ftrujillo.forecast.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }

}
