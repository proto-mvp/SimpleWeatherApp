package com.protomvp.simpleweatherapp.weatherinformation.reducers

import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.WeatherInformationUseCase
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.weatherinformation.FavouritePlace
import com.protomvp.simpleweatherapp.weatherinformation.PresentationModel
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

class LoadInformationReducerTest {
    private val useCase: WeatherInformationUseCase = mockk()

    private lateinit var subject: LoadInformationReducer

    @Before
    fun setUp() {
        subject = LoadInformationReducer(useCase)
    }

    @Test
    fun `reduce without favourites`() = runBlockingTest {
        val response: CityWeatherInformation = mockk()
        coEvery { useCase.execute(any()) } returns UseCaseResult.Success(response)

        val state = subject.reduce(WeatherInformationState.ScreenInfo(PresentationModel()), "query")

        expectThat(state).isA<WeatherInformationState.ScreenInfo>().and {
            get { this.presentationModel.cityWeatherInformation } isEqualTo response
        }

        coVerify { useCase.execute("query") }
    }

    @Test
    fun `reduce with favourites`() = runBlockingTest {
        val response: CityWeatherInformation = mockk()
        val places = listOf(FavouritePlace("A"))
        coEvery { useCase.execute(any()) } returns UseCaseResult.Success(response)

        val state = subject.reduce(
            WeatherInformationState.ScreenInfo(PresentationModel(favouritePlaces = places)),
            "query"
        )

        expectThat(state).isA<WeatherInformationState.ScreenInfo>().and {
            get { this.presentationModel.cityWeatherInformation } isEqualTo response
            get { this.presentationModel.favouritePlaces } isEqualTo places
        }

        coVerify { useCase.execute("query") }
    }

    @Test
    fun `reduce fails`() = runBlockingTest {
        val places = listOf(FavouritePlace("A"))

        coEvery { useCase.execute(any()) } returns UseCaseResult.Fail.NetworkRepositoryFailure(404)

        val state = subject.reduce(
            WeatherInformationState.ScreenInfo(PresentationModel(favouritePlaces = places)),
            "query"
        )

        expectThat(state).isA<WeatherInformationState.FailedGettingResults>()

        coVerify { useCase.execute("query") }
    }
}