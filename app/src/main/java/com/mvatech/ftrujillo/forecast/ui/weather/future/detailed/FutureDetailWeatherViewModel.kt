package com.mvatech.ftrujillo.forecast.ui.weather.future.detailed

import androidx.lifecycle.ViewModel;
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepository
import com.mvatech.ftrujillo.forecast.data.providers.UnitProvider
import com.mvatech.ftrujillo.forecast.internal.lazyDeferred
import com.mvatech.ftrujillo.forecast.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    detailDate: LocalDate,
    forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider ) {

    val weather by lazyDeferred{
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}
