package com.protomvp.simpleweatherapp

import android.os.Bundle
import android.view.View
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.protomvp.simpleweatherapp.databinding.FragmentFirstBinding
import com.protomvp.simpleweatherapp.ui.screens.WeatherResultsScreen
import com.protomvp.simpleweatherapp.ui.theme.SimpleWeatherAppTheme
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationIntent
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationSideEffect
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationState
import com.protomvp.simpleweatherapp.weatherinformation.WeatherInformationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null

    private val viewModel: WeatherInformationViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            viewModel.start()
        }
        bind {
            composeView.setContent {
                SimpleWeatherAppTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        val state by viewModel.getStateFlow()
                            .collectAsState(WeatherInformationState.Introduction)
                        val sideEffect by viewModel.getSideEffectFlow()
                            .collectAsState(initial = WeatherInformationSideEffect.Loading)

                        WeatherResultsScreen(state = state,
                            sideEffect = sideEffect,
                            sendAction = {
                                viewModel.sendIntent(WeatherInformationIntent.NewCityRequest(query = it))
                            },
                            addFavouriteAction = {
                                viewModel.sendIntent(
                                    WeatherInformationIntent.AddCityFavourite(it)
                                )
                            },
                            removeFavouriteAction = {
                                viewModel.sendIntent(WeatherInformationIntent.RemoveCityFavourite(it))
                            })
                    }
                }
            }
        }
    }

    private fun bind(block: FragmentFirstBinding.() -> Unit) {
        binding?.apply(block)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}