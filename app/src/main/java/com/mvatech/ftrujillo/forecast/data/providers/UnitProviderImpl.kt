package com.mvatech.ftrujillo.forecast.data.providers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.mvatech.ftrujillo.forecast.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"
class UnitProviderImpl(context: Context) : UnitProvider {
    private val appContext = context.applicationContext

    private val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM,UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}