package com.mvatech.ftrujillo.forecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvatech.ftrujillo.forecast.data.db.entity.CurrentWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.entity.FutureWeatherEntry
import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation

const val DATABASE_NAME = "forecast.db"

@Database(
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase(): RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao():WeatherLocationDao
    abstract fun futureWeatherDao(): FutureWeatherDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context)=
                Room.databaseBuilder(context.applicationContext,
                    ForecastDatabase:: class.java, DATABASE_NAME)
                    .build()
    }
}