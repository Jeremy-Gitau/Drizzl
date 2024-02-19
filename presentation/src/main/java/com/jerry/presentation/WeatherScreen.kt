package com.jerry.presentation

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.domain.models.WeatherDomain
import com.jerry.presentation.viewModel.WeatherState
import com.jerry.presentation.viewModel.WeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchLocation()
            viewModel.viewModelScope.launch {
                // Add a delay of 3000 milliseconds (3 seconds)
                delay(1000)
                viewModel.fetchWeatherData()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!state.isGranted) {
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.fetchWeatherData()
        }
        if (state.weatherData == null) {
            viewModel.fetchWeatherData()
        }
        if (state.latitude == null && state.longitude == null) {
            viewModel.fetchLocation()
        }
    }

    WeatherScreenContent(
        state = state,
        fetchWeatherData = viewModel::fetchWeatherData
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreenContent(
    state: WeatherState,
    fetchWeatherData: () -> Unit
) {
    when {
        state.isLoading -> {
            // Display loading indicator
            Column(
                modifier = Modifier.size(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }

        }

        state.error != null -> {
            // Display error message and retry option
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
            WeatherDetails(
                weatherData = state.weatherData,
                imageUrl = state.imageUrl!!,
                formatTime = state.formayTime,
                formatDay = state.formatDay
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetails(
    weatherData: WeatherDomain,
    imageUrl: String,
    formatTime: String?,
    formatDay: String?
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = colorScheme.surface
    ) {

        Column {
            ExtendedWeatherCard(
                weatherData = weatherData,
                imageUrl = imageUrl,
                formatDay = formatDay,
                formatTime = formatTime
            )

            OutlinedCardWithIconAndText(
                weatherData = weatherData,
                imageUrl = imageUrl
            )
        }


    }
}


fun isDay(weatherData: WeatherDomain): Boolean = weatherData.current.is_day.toInt() == 1
