package com.jacqulin.gainly.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PingServerInitializer : Initializer<Unit> {

    @OptIn(DelicateCoroutinesApi::class)
    override fun create(context: Context) {
        val healthApi = EntryPointAccessors.fromApplication(
            context.applicationContext,
            HealthApiEntryPoint::class.java
        ).healthApiService()

        GlobalScope.launch {
            try {
                healthApi.pingServer()
                Log.d("HealthPingInitializer", "Ping successful")
            } catch (e: Exception) {
                Log.e("HealthPingInitializer", "Ping failed", e)
            }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> = emptyList()
}