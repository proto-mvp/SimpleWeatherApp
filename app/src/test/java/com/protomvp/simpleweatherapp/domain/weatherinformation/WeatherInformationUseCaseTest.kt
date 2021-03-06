package com.protomvp.simpleweatherapp.domain.weatherinformation

import com.protomvp.simpleweatherapp.common.domain.RepositoryResult
import com.protomvp.simpleweatherapp.common.domain.UseCaseResult
import com.protomvp.simpleweatherapp.domain.weatherinformation.model.CityWeatherInformation
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.CityWeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA

class WeatherInformationUseCaseTest {
    lateinit var subject: WeatherInformationUseCase
    private val cityWeatherRepository: CityWeatherRepository = mockk()

    @Before
    fun setUp() {
        subject = WeatherInformationUseCase(cityWeatherRepository)
    }

    @Test
    fun `execute invokes repository`() = runBlockingTest {
        val city = "test"

        coEvery { cityWeatherRepository.getWeather(any()) } returns RepositoryResult.Success(mockk())

        val result = subject.execute(city)

        expectThat(result).isA<UseCaseResult.Success<CityWeatherInformation>>()
        coVerify { cityWeatherRepository.getWeather(city) }
    }
}