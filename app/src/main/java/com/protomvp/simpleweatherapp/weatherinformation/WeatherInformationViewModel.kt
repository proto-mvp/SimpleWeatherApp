package com.protomvp.simpleweatherapp.weatherinformation

import androidx.lifecycle.viewModelScope
import com.protomvp.simpleweatherapp.common.presentation.BaseViewModel
import com.protomvp.simpleweatherapp.weatherinformation.reducers.FavouriteActionsReducer
import com.protomvp.simpleweatherapp.weatherinformation.reducers.FavouritesReducer
import com.protomvp.simpleweatherapp.weatherinformation.reducers.LoadInformationByGpsLocationReducer
import com.protomvp.simpleweatherapp.weatherinformation.reducers.LoadInformationReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
class WeatherInformationViewModel @Inject constructor(
    private val loadInformationReducer: LoadInformationReducer,
    private val favouritesReducer: FavouritesReducer,
    private val favouriteActionsReducer: FavouriteActionsReducer,
    private val loadInformationGpsReducer: LoadInformationByGpsLocationReducer,
) :
    BaseViewModel<WeatherInformationState, WeatherInformationIntent, WeatherInformationSideEffect>() {
    private data class StoredResult(val gpsLong: String = "", val gpsLat: String = "")

    private var store = StoredResult()

    override fun sendIntent(intent: WeatherInformationIntent) {
        when (intent) {
            is WeatherInformationIntent.NewCityRequest -> loadInfo(intent.query)
            is WeatherInformationIntent.AddCityFavourite -> setFavourite(intent)
            is WeatherInformationIntent.RemoveCityFavourite -> setFavourite(intent)
            is WeatherInformationIntent.NewLocationRequest -> {
                store = store.copy(gpsLat = intent.lat, gpsLong = intent.lon)
                loadInfo()
            }
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

    private fun loadInfo() = viewModelScope.launch {
        WeatherInformationSideEffect.Loading.send()
        when (val state =
            loadInformationGpsReducer.reduce(currentState(), store.gpsLat, store.gpsLong)) {
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