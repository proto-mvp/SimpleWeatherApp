package com.protomvp.simpleweatherapp.di

import androidx.fragment.app.FragmentFactory
import com.protomvp.simpleweatherapp.MainActivity
import dagger.Component

@Component(
    modules = [
        FragmentFactoryModule::class,
        ViewModelFactoryModule::class,
        FragmentsModule::class,
        ViewModelsModule::class
    ]
)
interface ApplicationComponent {
    val fragmentFactory: FragmentFactory

    fun inject(activity: MainActivity)
}

