package com.jerry.data

import android.content.Context
import com.jerry.data.repository.WeatherRepositoryImpl
import com.jerry.domain.repository.WeatherRepository
import com.jerry.sources.remote.DrizzlApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesWeatherRepository(api: DrizzlApi): WeatherRepository = WeatherRepositoryImpl(api)

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context
}