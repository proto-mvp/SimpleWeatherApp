package com.protomvp.simpleweatherapp.weatherinformation.reducers

import com.protomvp.simpleweatherapp.common.domain.cast
import com.protomvp.simpleweatherapp.domain.weatherinformation.WeatherInformationByGpsUseCase
import com.protomvp.simpleweatherapp.weatherinformation.PresentationModel
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState
import javax.inject.Inject

class LoadInformationByGpsLocationReducer @Inject constructor(
    private val useCase: WeatherInformationByGpsUseCase,
) {
    suspend fun reduce(
        lastState: WeatherInformationState?, lat: String, lng: String
    ): WeatherInformationState {
        return useCase.execute(lat, lng).cast(
            onSuccess = {
                if (lastState is WeatherInformationState.ScreenInfo) {
                    val newState = lastState.copy(
                        presentationModel = lastState.presentationModel.copy(cityWeatherInformation = it)
                    )
                    newState
                } else {
                    WeatherInformationState.ScreenInfo(PresentationModel(cityWeatherInformation = it))
                }
            },
            onFail = {
                WeatherInformationState.FailedGettingResults
            })
    }
}