package com.protomvp.simpleweatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.protomvp.simpleweatherapp.common.domain.onSuccess
import com.protomvp.simpleweatherapp.common.network.ApiUrlQueryBuilder
import com.protomvp.simpleweatherapp.common.network.NetworkClient
import com.protomvp.simpleweatherapp.databinding.FragmentFirstBinding
import com.protomvp.simpleweatherapp.domain.weatherinformation.WeatherInformationUseCase
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.CityWeatherRepository
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.ApiUrlProvider
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.CurrentWeatherService
import com.protomvp.simpleweatherapp.domain.weatherinformation.repositories.networksource.WeatherApiKeyProvider
import com.protomvp.simpleweatherapp.ui.theme.SimpleWeatherAppTheme

class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            WeatherInformationUseCase(
                CityWeatherRepository(
                    CurrentWeatherService(
                        NetworkClient(ApiUrlProvider()), ApiUrlQueryBuilder(WeatherApiKeyProvider())
                    )
                )
            ).execute("London,UK")
                .onSuccess {
                    Log.d("SimpleWeatherApp", it.toString())
                }
        }
        binding.composeView.setContent {
            SimpleWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Text("Loading")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}