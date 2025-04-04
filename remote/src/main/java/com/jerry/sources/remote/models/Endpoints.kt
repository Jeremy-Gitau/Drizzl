package com.jerry.sources.remote.models

import com.jerry.sources.remote.BuildConfig

sealed class Endpoints(private val path: String) {

    private val baseUrl = BuildConfig.baseurl

    val url: String
        get() = "$baseUrl$path"

    data class GetCurrentWeather(
        val query: String,
        val apikey: String
    ) : Endpoints("/forecast.json?q=$query&key=$apikey&days=7")

}