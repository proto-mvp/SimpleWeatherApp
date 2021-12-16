package com.protomvp.simpleweatherapp

import android.app.Application
import com.protomvp.simpleweatherapp.di.ApplicationComponent
import com.protomvp.simpleweatherapp.di.DaggerApplicationComponent

class WeatherApplication : Application() {
    private lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()


        applicationComponent = DaggerApplicationComponent.builder()
            .build()
    }

    fun appComponent() = applicationComponent
}