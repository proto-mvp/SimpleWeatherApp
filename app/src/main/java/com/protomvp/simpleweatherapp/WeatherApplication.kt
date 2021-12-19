package com.protomvp.simpleweatherapp

import android.app.Application
import com.protomvp.simpleweatherapp.di.ApplicationComponent
import com.protomvp.simpleweatherapp.di.CoreModule
import com.protomvp.simpleweatherapp.di.DaggerApplicationComponent

class WeatherApplication : Application() {
    private lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()


        applicationComponent = DaggerApplicationComponent.builder()
            .coreModule(CoreModule(applicationContext))
            .build()
    }

    fun appComponent() = applicationComponent
}