package com.protomvp.simpleweatherapp.weatherinformation

import androidx.lifecycle.viewModelScope
import com.protomvp.simpleweatherapp.common.domain.onFail
import com.protomvp.simpleweatherapp.common.domain.onSuccess
import com.protomvp.simpleweatherapp.common.presentation.BaseViewModel
import com.protomvp.simpleweatherapp.common.presentation.SideEffect
import com.protomvp.simpleweatherapp.common.presentation.ViewModelIntent
import com.protomvp.simpleweatherapp.common.presentation.ViewModelState
import com.protomvp.simpleweatherapp.domain.favouritecities.GetFavouritePlacesUseCase
import com.protomvp.simpleweatherapp.domain.weatherinformation.WeatherInformationUseCase
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class WeatherInformationState : ViewModelState {
    object Introduction : WeatherInformationState()
    data class NewWeatherInfo(val weatherResponse: CityWeatherInformation) :
        WeatherInformationState()

    data class ScreenInfo(val presentationModel: PresentationModel) : WeatherInformationState()

    object FailedGettingResults : WeatherInformationState()
}

sealed class WeatherInformationIntent : ViewModelIntent {
    class NewCityRequest(val query: String) : WeatherInformationIntent()
    class RemoveCityFavourite(val cityName: String) : WeatherInformationIntent()
    class AddCityFavourite(val cityName: String) : WeatherInformationIntent()
}

sealed class WeatherInformationSideEffect : SideEffect {
    object Loading : WeatherInformationSideEffect()
}

@ExperimentalCoroutinesApi
class WeatherInformationViewModel @Inject constructor(
    private val useCase: WeatherInformationUseCase,
    private val placesUseCase: GetFavouritePlacesUseCase
) :
    BaseViewModel<WeatherInformationState, WeatherInformationIntent, WeatherInformationSideEffect>() {

    override fun sendIntent(intent: WeatherInformationIntent) {
        when (intent) {
            is WeatherInformationIntent.NewCityRequest -> loadInfo(intent.query)
        }
    }

    override fun start() {

        viewModelScope.launch {
            placesUseCase.execute()
                .onSuccess {
                    val state = currentState()
                    if (state is WeatherInformationState.ScreenInfo) {
                        val newState = state.copy(
                            presentationModel = state.presentationModel.copy(favouritePlaces = it.map {
                                FavouritePlace(
                                    it
                                )
                            })
                        )
                        newState.updateState()
                    } else {
                        WeatherInformationState.ScreenInfo(PresentationModel(favouritePlaces = it.map {
                            FavouritePlace(
                                it
                            )
                        })).updateState()
                    }
                }
        }
    }

    private fun loadInfo(query: String) = viewModelScope.launch {
        WeatherInformationSideEffect.Loading.send()

        useCase.execute(query)
            .onSuccess {
                // WeatherInformationState.ScreenInfo(PresentationModel(it)).updateState()
                val state = currentState()

                if (state is WeatherInformationState.ScreenInfo) {
                    val newState = state.copy(
                        presentationModel = state.presentationModel.copy(cityWeatherInformation = it)
                    )
                    newState.updateState()
                } else {
                    WeatherInformationState.ScreenInfo(PresentationModel(cityWeatherInformation = it))
                        .updateState()
                }
//                WeatherInformationState.NewWeatherInfo(it).updateState()
            }
            .onFail {
                WeatherInformationState.FailedGettingResults.updateState()
            }
    }
}