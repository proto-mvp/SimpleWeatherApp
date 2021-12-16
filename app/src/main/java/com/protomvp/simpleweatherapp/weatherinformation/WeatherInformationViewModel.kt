package com.protomvp.simpleweatherapp.weatherinformation

import androidx.lifecycle.viewModelScope
import com.protomvp.simpleweatherapp.common.domain.onFail
import com.protomvp.simpleweatherapp.common.domain.onSuccess
import com.protomvp.simpleweatherapp.common.presentation.BaseViewModel
import com.protomvp.simpleweatherapp.common.presentation.SideEffect
import com.protomvp.simpleweatherapp.common.presentation.ViewModelIntent
import com.protomvp.simpleweatherapp.common.presentation.ViewModelState
import com.protomvp.simpleweatherapp.domain.weatherinformation.WeatherInformationUseCase
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.models.WeatherResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class WeatherInformationState : ViewModelState {
    object Introduction : WeatherInformationState()
    class NewWeatherInfo(val weatherResponse: WeatherResponse) : WeatherInformationState()
    object FailedGettingResults : WeatherInformationState()
}

sealed class WeatherInformationIntent : ViewModelIntent {
    class NewCityRequest(val query: String) : WeatherInformationIntent()
}

sealed class WeatherInformationSideEffect : SideEffect {
    object Loading : WeatherInformationSideEffect()
}

class WeatherInformationViewModel @Inject constructor(private val useCase: WeatherInformationUseCase) :
    BaseViewModel<WeatherInformationState, WeatherInformationIntent, WeatherInformationSideEffect>() {

    override fun sendIntent(intent: WeatherInformationIntent) {
        when (intent) {
            is WeatherInformationIntent.NewCityRequest -> loadInfo(intent.query)
        }
    }

    private fun loadInfo(query: String) = viewModelScope.launch {
        WeatherInformationSideEffect.Loading.send()

        useCase.execute(query)
            .onSuccess {
                WeatherInformationState.NewWeatherInfo(it).updateState()
            }
            .onFail {
                WeatherInformationState.FailedGettingResults.updateState()
            }
    }
}