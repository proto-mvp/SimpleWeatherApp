package com.protomvp.simpleweatherapp.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import com.protomvp.simpleweatherapp.MainActivity
import com.protomvp.simpleweatherapp.common.localpersistence.LocalStorage
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [
        CoreModule::class,
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

@Module
class CoreModule(
    private val context: Context
) {
    @Provides
    fun getContext() = context

    @Provides
    fun getLocalStorage() = LocalStorage(context)
}
