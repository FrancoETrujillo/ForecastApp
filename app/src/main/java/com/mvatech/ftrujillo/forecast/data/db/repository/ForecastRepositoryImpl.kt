package com.mvatech.ftrujillo.forecast.data.db.repository

import androidx.lifecycle.LiveData
import com.mvatech.ftrujillo.forecast.data.network.response.CurrentWeatherResponse
import com.mvatech.ftrujillo.forecast.data.db.CurrentWeatherDao
import com.mvatech.ftrujillo.forecast.data.db.WeatherLocationDao
import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.network.WeatherNetworkDataSource
import com.mvatech.ftrujillo.forecast.data.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao:CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {
    init{
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)

        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
       return withContext(Dispatchers.IO){
           initWeatherData()
           return@withContext if(metric) currentWeatherDao.getWeatherMetric()
           else currentWeatherDao.getWeatherImperial()
       }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value
        if(lastWeatherLocation == null ||
            locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean{
        val thirtyMinutesAgo = org.threeten.bp.ZonedDateTime.now().minusMinutes(30)
        return  lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}