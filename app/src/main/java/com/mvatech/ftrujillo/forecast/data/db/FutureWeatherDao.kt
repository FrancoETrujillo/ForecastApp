package com.mvatech.ftrujillo.forecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvatech.ftrujillo.forecast.data.db.entity.FutureWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.future.detail.ImperialDetailFutureWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.future.detail.MetricDetailFutureWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather where date(date) > date(:startDate)")
    fun getSimpleWeatherForecastMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) > date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getDetailWeatherForecastMetric(date: LocalDate) : LiveData<MetricDetailFutureWeatherEntry>

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getDetailWeatherForecastImperial(date: LocalDate) : LiveData<ImperialDetailFutureWeatherEntry>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep:LocalDate)
}