package com.jerry.sources.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jerry.sources.remote.models.CurrentWeatherDTO
import com.jerry.sources.remote.models.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrizzlApiTest {

    companion object {
        val responseBody = """
            {
                "location": {
                    "name": "Nairobi",
                    "region": "Nairobi Area",
                    "country": "Kenya",
                    "lat": -1.28,
                    "lon": 36.82,
                    "tz_id": "Africa/Nairobi",
                    "localtime_epoch": 1707842097,
                    "localtime": "2024-02-13 19:34"
                },
                "current": {
                    "last_updated_epoch": 1707841800,
                    "last_updated": "2024-02-13 19:30",
                    "temp_c": 23.1,
                    "temp_f": 73.6,
                    "is_day": 0,
                    "condition": {
                          "text": "Clear ",
                          "icon": "//cdn.weatherapi.com/weather/64x64/night/113.png",
                          "code": 1000
                    }
                }
            }
        """.trimIndent()
    }

    private fun generateClient(engine: HttpClientEngine) = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
    }


    @Test
    fun getCurrentWeather() = runBlocking {

        val fakeEngine = MockEngine {
            respond(
                content = responseBody,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val json = Json { ignoreUnknownKeys = true }
        val fakeClient = generateClient(engine = fakeEngine)
        val drizzlApi = DrizzlApi(client = fakeClient)
        val actual = runBlocking {
            drizzlApi.getCurrentWeather("kenya")
        }

        // Handle the case when decoding fails
        val expected = try {
            json.decodeFromString(CurrentWeatherDTO.ResponseBody.serializer(), responseBody)
        } catch (e: Exception) {
            // Return a NetworkResult.Error if decoding fails
            NetworkResult.Error("Failed to decode response: ${e.message}")
        }

        Assert.assertEquals(expected, (actual as? NetworkResult.Success)?.data)
    }
}