package com.mvatech.ftrujillo.forecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvatech.ftrujillo.forecast.data.db.entity.CURRENT_WEATHER_ID
import com.mvatech.ftrujillo.forecast.data.db.entity.CurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import kotlinx.coroutines.selects.select

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}