package com.mvatech.ftrujillo.forecast

import android.app.Application
import com.mvatech.ftrujillo.forecast.data.db.ForecastDatabase
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepository
import com.mvatech.ftrujillo.forecast.data.db.repository.ForecastRepositoryImpl
import com.mvatech.ftrujillo.forecast.data.network.response.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance())}
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWheatherApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }



    }
}