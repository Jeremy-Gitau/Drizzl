package com.jerry.data

import com.jerry.data.repository.WeatherRepositoryImpl
import com.jerry.domain.repository.WeatherRepository
import com.jerry.sources.remote.DrizzlApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesWeatherRepository(api: DrizzlApi): WeatherRepository = WeatherRepositoryImpl(api)
}