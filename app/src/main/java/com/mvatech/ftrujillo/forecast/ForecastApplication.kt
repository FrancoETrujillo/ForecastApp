package com.mvatech.ftrujillo.forecast

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mvatech.ftrujillo.forecast.data.db.ForecastDatabase
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepository
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepositoryImpl
import com.mvatech.ftrujillo.forecast.data.network.*
import com.mvatech.ftrujillo.forecast.data.providers.LocationProvider
import com.mvatech.ftrujillo.forecast.data.providers.LocationProviderImpl
import com.mvatech.ftrujillo.forecast.data.providers.UnitProvider
import com.mvatech.ftrujillo.forecast.data.providers.UnitProviderImpl
import com.mvatech.ftrujillo.forecast.ui.weather.current.CurrentWeatherViewModelFactory
import com.mvatech.ftrujillo.forecast.ui.weather.future.detailed.FutureDetailWeatherViewModelFactory
import com.mvatech.ftrujillo.forecast.ui.weather.future.list.FutureListWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication : Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance())}
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao()}
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao()}
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao()}
        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(
                instance()
            )
        }
        bind() from singleton { ApixuWheatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton {
            WeatherNetworkDataSourceImpl(
                instance()
            )
        }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory{ detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance())}

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}