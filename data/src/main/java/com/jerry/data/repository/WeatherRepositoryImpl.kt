package com.jerry.data.repository

import com.jerry.data.mapper.toDomain
import com.jerry.domain.models.DataResult
import com.jerry.domain.models.WeatherDomain
import com.jerry.domain.repository.WeatherRepository
import com.jerry.sources.remote.DrizzlApi
import com.jerry.sources.remote.models.NetworkResult
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: DrizzlApi
): WeatherRepository {

    override suspend fun getCurrentWeather(query: String): DataResult<WeatherDomain> {

        return when (val result = api.getCurrentWeather(query = query)){
            is NetworkResult.Error -> DataResult.Error(message = result.message)
            is NetworkResult.Success ->{
                val data = result.data.toDomain()

                DataResult.Success(data = data)
            }
        }
    }
}