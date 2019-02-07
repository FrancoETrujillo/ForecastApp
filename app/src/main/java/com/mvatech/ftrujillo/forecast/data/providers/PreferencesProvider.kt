package com.mvatech.ftrujillo.forecast.data.providers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

abstract class PreferencesProvider(context: Context) {
    private val appContext = context.applicationContext

    protected val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}