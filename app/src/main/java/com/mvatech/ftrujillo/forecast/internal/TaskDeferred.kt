package com.mvatech.ftrujillo.forecast.internal

import android.util.Log
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

fun <T> Task<T>.asDeferred(): Deferred<T>{
    val deferred = CompletableDeferred<T>()
    this.addOnSuccessListener { result ->
        Log.d("Franco", "Listener success on as deferred" + result.toString())

        deferred.complete(result)
    }

    this.addOnFailureListener{exception ->
        Log.d("Franco", "Listener Failure on as deferred")
        deferred.completeExceptionally(exception)
    }

    return deferred
}