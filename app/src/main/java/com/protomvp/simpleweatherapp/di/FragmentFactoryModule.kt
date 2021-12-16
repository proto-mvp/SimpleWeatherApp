package com.protomvp.simpleweatherapp.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.protomvp.simpleweatherapp.MainFragment
import com.protomvp.simpleweatherapp.di.factories.AppFragmentFactory
import com.protomvp.simpleweatherapp.di.keys.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentFactoryModule {
    @Binds
    abstract fun fragmentFactory(appFragmentFactory: AppFragmentFactory): FragmentFactory
}

@Module
abstract class FragmentsModule {
    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    abstract fun mainFragment(fragment: MainFragment): Fragment
}