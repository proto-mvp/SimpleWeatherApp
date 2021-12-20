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

class WeatherInformationByGpsUseCaseTest {

    lateinit var subject: WeatherInformationByGpsUseCase
    private val cityWeatherRepository: CityWeatherRepository = mockk()

    @Before
    fun setUp() {
        subject = WeatherInformationByGpsUseCase(cityWeatherRepository)
    }

    @Test
    fun `execute invokes repository`() = runBlockingTest {
        val coord = "test"

        coEvery { cityWeatherRepository.getWeather(any(), any()) } returns RepositoryResult.Success(
            mockk()
        )

        val result = subject.execute(coord, coord)

        expectThat(result).isA<UseCaseResult.Success<CityWeatherInformation>>()
        coVerify { cityWeatherRepository.getWeather(coord, coord) }
    }
}