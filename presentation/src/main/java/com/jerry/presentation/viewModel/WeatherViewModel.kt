package com.jerry.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.domain.models.DataResult
import com.jerry.domain.models.WeatherDomain
import com.jerry.domain.repository.PermissionHandler
import com.jerry.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class WeatherState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val weatherData: WeatherDomain? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val permissionHandler: PermissionHandler
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun fetchWeatherData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val locationResult = permissionHandler.getCurrentLocation()
            if (locationResult is DataResult.Success) {
                val location = locationResult.data
                val weatherResult = weatherRepo.getCurrentWeather(location.toString())

                if (weatherResult is DataResult.Success) {
                    _state.value = _state.value.copy(weatherData = weatherResult.data)
                } else {
                    _state.value = _state.value.copy(
                        error = "Failed to fetch weather data",
                        isLoading = false
                    )
                }
            } else {
                _state.value = _state.value.copy(
                    error = "Failed to fetch location",
                    isLoading = false
                )
            }
        }

    }
}