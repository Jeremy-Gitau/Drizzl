package com.jerry.sources.remote

import com.jerry.sources.remote.models.CurrentWeatherDTO
import com.jerry.sources.remote.models.Endpoints
import com.jerry.sources.remote.models.NetworkResult
import com.jerry.sources.remote.models.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DrizzlApi @Inject constructor(
    private val client: HttpClient
) {

    private val apikey = BuildConfig.apikey

    suspend fun getCurrentWeather(
        query: String
    ): NetworkResult<CurrentWeatherDTO.ResponseBody> =
        safeApiCall {

            val response: HttpResponse =
                client.get(Endpoints.GetCurrentWeather(apikey = apikey, query = query).url)

            val json = Json { ignoreUnknownKeys = true }
            val currentWeather: CurrentWeatherDTO.ResponseBody =
                json.decodeFromString(response.bodyAsText())

            currentWeather
        }
}