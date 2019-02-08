package com.mvatech.ftrujillo.forecast.data.providers

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.mvatech.ftrujillo.forecast.data.db.entity.WeatherLocation
import com.mvatech.ftrujillo.forecast.internal.asDeferred
import com.mvatech.ftrujillo.forecast.internal.exceptions.LocationPermissionNotGrantedException
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
    ) : PreferencesProvider(context), LocationProvider {

    private val appContext = context.applicationContext
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChange = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException){
            false
        }
        return deviceLocationChange || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        if(isUsingDeviceLocation()){
            try{
                val deviceLocation = getLastDeviceLocationAsync().await()
                if(deviceLocation == null){
                    Log.d("Franco","deviceLocation null returned location is: " + getCustomLocationName())
                    return "${getCustomLocationName()}"
                }
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException){
                Log.d("Franco","catch returned location is: " + getCustomLocationName())
                return "${getCustomLocationName()}"
            }
        } else {
            Log.d("Franco","else returned location is: " + getCustomLocationName())
            return "${getCustomLocationName()}"
        }
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if(!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocationAsync().await()
            ?: return false

        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon)> comparisonThreshold
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocationAsync(): Deferred<Location?> {
        return if(hasLocationPermission()) {
            Log.d("Franco","Permission granted looking for location")
            fusedLocationProviderClient.lastLocation.asDeferred()
        }
        else
            throw LocationPermissionNotGrantedException()


    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun hasLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean{
        if(!isUsingDeviceLocation()){
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

}