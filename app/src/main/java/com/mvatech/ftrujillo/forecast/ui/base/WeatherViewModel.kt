package com.mvatech.ftrujillo.forecast.ui.base

import androidx.lifecycle.ViewModel
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepository
import com.mvatech.ftrujillo.forecast.data.providers.UnitProvider
import com.mvatech.ftrujillo.forecast.internal.UnitSystem
import com.mvatech.ftrujillo.forecast.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) :ViewModel(){
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit:Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred{
        forecastRepository.getWeatherLocation()
    }
}