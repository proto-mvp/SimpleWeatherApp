package com.protomvp.simpleweatherapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.protomvp.simpleweatherapp.di.factories.AppViewModelFactory
import com.protomvp.simpleweatherapp.di.keys.ViewModelKey
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}


@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherInformationViewModel::class)
    abstract fun weatherViewModel(viewModel: WeatherInformationViewModel): ViewModel
}