package com.jerry.domain.repository

import com.jerry.domain.models.DataResult
import com.jerry.domain.models.WeatherDomain

interface WeatherRepository {
    suspend fun getCurrentWeather(query: String): DataResult<WeatherDomain>
}