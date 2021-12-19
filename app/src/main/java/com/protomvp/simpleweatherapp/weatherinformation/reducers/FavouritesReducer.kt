package com.protomvp.simpleweatherapp.weatherinformation.reducers

import com.protomvp.simpleweatherapp.common.domain.cast
import com.protomvp.simpleweatherapp.domain.favouritecities.GetFavouritePlacesUseCase
import com.protomvp.simpleweatherapp.weatherinformation.FavouritePlace
import com.protomvp.simpleweatherapp.weatherinformation.PresentationModel
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState
import javax.inject.Inject

class FavouritesReducer @Inject constructor(
    private val placesUseCase: GetFavouritePlacesUseCase,
) {
    suspend fun reduce(
        lastState: WeatherInformationState?,
    ): WeatherInformationState {
        return placesUseCase.execute().cast(
            onSuccess = { value ->
                if (lastState is WeatherInformationState.ScreenInfo) {
                    val newState = lastState.copy(
                        presentationModel = lastState.presentationModel.copy(
                            favouritePlaces = value.toFavourites()
                        )
                    )
                    newState
                } else {
                    WeatherInformationState.ScreenInfo(
                        PresentationModel(
                            favouritePlaces = value.toFavourites()
                        )
                    )
                }
            }, onFail = { WeatherInformationState.FailedOnFavouritesAction })
    }

    private fun List<String>.toFavourites() = this.map { FavouritePlace(it) }
}