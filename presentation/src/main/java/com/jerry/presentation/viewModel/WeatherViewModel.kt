package com.jerry.presentation.viewModel

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jerry.domain.models.DataResult
import com.jerry.domain.models.ForecastDay
import com.jerry.domain.models.Hour
import com.jerry.domain.models.WeatherDomain
import com.jerry.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField
import javax.inject.Inject

data class WeatherState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val weatherData: WeatherDomain? = null,
    val isGranted: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val imageUrl: String? = null,
    val formatDay: String? = null,
    val formatTime: String? = null,
    val forecastData: List<ForecastDay>? = null,
    val hourData: List<List<Hour>>? = null,
    val formattedForecastDayDates: List<String>? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val context: Context
) : ViewModel() {

    private var locationPermissionLauncher: ActivityResultLauncher<String>? = null

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeatherData() {
        viewModelScope.launch {


            if (isPermissionGranted()) {
                _state.value = _state.value.copy(
                    isGranted = true,
                    isLoading = true
                )
                val latitude = state.value.latitude
                val longitude = state.value.longitude

                if (latitude != null && longitude != null) {
                    val location = "$latitude,$longitude"

                    val weatherResult = weatherRepo.getCurrentWeather(location).also {
                        Log.e("locationData", location)
                    }

                    //check if fetch weather data is successful and change some states
                    if (weatherResult is DataResult.Success) {

                        // Extract forecast data
                        val forecastData = weatherResult.data.forecast.forecastday

                        _state.value = _state.value.copy(

                            weatherData = weatherResult.data.also {
                                Log.e("weatherData", weatherResult.data.toString())
                            },
                            isLoading = false,
                            formatDay = state.value.weatherData?.location?.let { formatDay(it.localtime) },
                            formatTime = state.value.weatherData?.location?.let { formatTime(it.localtime) },
                            forecastData = forecastData
                        )

                        //handle the forecast data and store their states in weather state
                        forecastData.let { forecast ->

                            val hourDataList = mutableListOf<List<Hour>>()
                            val formattedDates = mutableListOf<String>()
                            // Iterate through each forecast day
                            forecast.forEach { forecastDay ->
                                // Access the hour data for each forecast day
                                val hourData = forecastDay.hour

                                hourDataList.add(hourData)
                                formattedDates.add(formatDay(forecastDay.date))

                                _state.value = _state.value.copy(
                                    hourData = hourDataList,
                                    formattedForecastDayDates = formattedDates
                                ).also {
                                    Log.e("formattedDates", formattedDates.toString())
                                }


                            }
                        }

                        if (!_state.value.weatherData?.current?.condition?.icon?.startsWith("https://")!!) {
                            _state.value = _state.value.copy(
                                imageUrl = "https://${
                                    state.value.weatherData!!.current.condition.icon.substringAfter(
                                        "http://"
                                    )
                                }"
                            )
                        } else {
                            _state.value =
                                _state.value.copy(imageUrl = _state.value.weatherData!!.current.condition.icon)
                        }
                    } else {
                        _state.value = _state.value.copy(
                            error = "Failed to fetch weather data",
                            isLoading = false,
                            isGranted = false
                        ).also {
                            Log.e("weatherData", weatherResult.toString())
                        }
                    }
                } else {
                    fetchLocation()
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isGranted = false
                )
                // Request location permission
                locationPermissionLauncher?.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun fetchLocation() {
        if (isPermissionGranted()) {
            try {
                val fusedLocationClient: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        if (it != null) {
                            DataResult.Success(it)
                            _state.value = _state.value.copy(
                                latitude = it.latitude,
                                longitude = it.longitude,
                                isGranted = true
                            )
                        }
                    }.addOnFailureListener {
                        _state.value = _state.value.copy(
                            error = "Location results not fetched",
                            isLoading = false
                        )
                    }
            } catch (e: SecurityException) {
                DataResult.Error("Location permission not granted")
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error fetching location: ${e.message}",
                    isLoading = false
                )
            }
        } else {
            _state.value = _state.value.copy(
                isLoading = false,
                isGranted = false
            )
            locationPermissionLauncher?.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDay(localTime: String): String {
        return try {
            val formatter = if (localTime.contains(":")) {
                // If the string contains a colon (':'), it includes time information
                DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
            } else {
                // If no colon is present, assume it's just a date
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            }
            val dateString = LocalDate.parse(localTime, formatter)
            val parsedDate = LocalDate.parse(dateString.toString())

            // Define a formatter to format the date as the day of the week (e.g., "Monday")
            val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE")

            // Format the parsed date as the day of the week
            parsedDate.format(dayOfWeekFormatter)
        } catch (e: DateTimeParseException) {
            Log.e("DateTimeParseException", "Error parsing date: $localTime")
            ""
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatTime(localTime: String): String {

        // Parse the input date-time string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")

        // Parse the time string with optional hour-of-day field (H)
        val optionalFormatter = DateTimeFormatterBuilder()
            .append(formatter)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0) // Default to 0 if hour is missing
            .toFormatter()

        val dateTime = LocalTime.parse(localTime, optionalFormatter)

        // Format the time
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}
