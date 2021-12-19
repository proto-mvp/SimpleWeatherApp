package com.protomvp.simpleweatherapp.weatherinformation

import androidx.lifecycle.viewModelScope
import com.protomvp.simpleweatherapp.common.presentation.BaseViewModel
import com.protomvp.simpleweatherapp.weatherinformation.reducers.FavouriteActionsReducer
import com.protomvp.simpleweatherapp.weatherinformation.reducers.FavouritesReducer
import com.protomvp.simpleweatherapp.weatherinformation.reducers.LoadInformationReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
class WeatherInformationViewModel @Inject constructor(
    private val loadInformationReducer: LoadInformationReducer,
    private val favouritesReducer: FavouritesReducer,
    private val favouriteActionsReducer: FavouriteActionsReducer
) :
    BaseViewModel<WeatherInformationState, WeatherInformationIntent, WeatherInformationSideEffect>() {

    override fun sendIntent(intent: WeatherInformationIntent) {
        when (intent) {
            is WeatherInformationIntent.NewCityRequest -> loadInfo(intent.query)
            is WeatherInformationIntent.AddCityFavourite -> setFavourite(intent)
            is WeatherInformationIntent.RemoveCityFavourite -> setFavourite(intent)
        }
    }

    override fun start() {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            when (val state = favouritesReducer.reduce(currentState())) {
                is WeatherInformationState.FailedOnFavouritesAction -> WeatherInformationSideEffect.FailedGettingFavourites.send()
                else -> state.updateState()
            }
        }
    }

    private fun loadInfo(query: String) = viewModelScope.launch {
        WeatherInformationSideEffect.Loading.send()
        when (val state = loadInformationReducer.reduce(currentState(), query)) {
            is WeatherInformationState.FailedGettingResults -> WeatherInformationSideEffect.FailedGettingResults.send()
            else -> state.updateState()
        }
    }

    private fun setFavourite(intent: WeatherInformationIntent) {
        viewModelScope.launch {
            when (val state = favouriteActionsReducer.reduce(currentState(), intent)) {
                is WeatherInformationState.FailedOnFavouritesAction -> WeatherInformationSideEffect.FailedGettingFavourites.send()
                else -> state.updateState()
            }
        }
    }
}