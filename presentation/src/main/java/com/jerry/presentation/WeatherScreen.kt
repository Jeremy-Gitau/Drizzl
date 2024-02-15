package com.jerry.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jerry.domain.models.WeatherDomain
import com.jerry.presentation.viewModel.WeatherState
import com.jerry.presentation.viewModel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherData()
    }

    WeatherScreenContent(
        state = state,
        fetchWeatherData = viewModel::fetchWeatherData
    )
}

@Composable
fun WeatherScreenContent(
    state: WeatherState,
    fetchWeatherData: () -> Unit
) {
    when {
        state.isLoading -> {
            // Display loading indicator
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }
        state.error != null -> {
            // Display error message and retry option
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Error: ${state.error}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { fetchWeatherData() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Retry")
                }
            }
        }
        state.weatherData != null -> {
            // Display weather details
            WeatherDetails(weatherData = state.weatherData)
        }
    }
}


@Composable
fun WeatherDetails(weatherData: WeatherDomain) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Location: ${weatherData.location.name}")
        Text(text = "Temperature: ${weatherData.current.temp_c}°C")
        Text(text = "Condition: ${weatherData.current.condition.text}")
        // Add more weather details as needed
    }
}