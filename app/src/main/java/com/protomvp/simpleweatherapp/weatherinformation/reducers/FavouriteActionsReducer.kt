package com.protomvp.simpleweatherapp.weatherinformation.reducers

import com.protomvp.simpleweatherapp.common.domain.cast
import com.protomvp.simpleweatherapp.domain.favouritecities.AddFavouritePlaceUseCase
import com.protomvp.simpleweatherapp.domain.favouritecities.RemoveFavouritePlaceUseCase
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationIntent
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState
import javax.inject.Inject

class FavouriteActionsReducer @Inject constructor(
    private val favouritesReducer: FavouritesReducer,
    private val addFavouritePlaceUseCase: AddFavouritePlaceUseCase,
    private val removeFavouritePlaceUseCase: RemoveFavouritePlaceUseCase
) {
    suspend fun reduce(
        lastState: WeatherInformationState?, intent: WeatherInformationIntent
    ): WeatherInformationState {
        return when (intent) {
            is WeatherInformationIntent.AddCityFavourite -> addFavouritePlaceUseCase.execute(intent.cityName)
                .cast(
                    onSuccess = { favouritesReducer.reduce(lastState) },
                    onFail = { WeatherInformationState.FailedOnFavouritesAction })
            is WeatherInformationIntent.RemoveCityFavourite -> removeFavouritePlaceUseCase.execute(
                intent.cityName
            ).cast(
                onSuccess = { favouritesReducer.reduce(lastState) },
                onFail = { WeatherInformationState.FailedOnFavouritesAction })
            else -> WeatherInformationState.FailedOnFavouritesAction
        }
    }
}