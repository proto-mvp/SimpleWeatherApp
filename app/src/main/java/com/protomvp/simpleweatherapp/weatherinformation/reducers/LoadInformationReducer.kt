package com.protomvp.simpleweatherapp.weatherinformation.reducers

import com.protomvp.simpleweatherapp.common.domain.cast
import com.protomvp.simpleweatherapp.domain.weatherinformation.WeatherInformationUseCase
import com.protomvp.simpleweatherapp.weatherinformation.PresentationModel
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState
import javax.inject.Inject

class LoadInformationReducer @Inject constructor(
    private val useCase: WeatherInformationUseCase,
) {
    suspend fun reduce(
        lastState: WeatherInformationState?, query: String
    ): WeatherInformationState {
        return useCase.execute(query).cast(
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