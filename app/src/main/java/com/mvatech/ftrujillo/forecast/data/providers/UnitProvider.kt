package com.mvatech.ftrujillo.forecast.data.providers

import com.mvatech.ftrujillo.forecast.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}