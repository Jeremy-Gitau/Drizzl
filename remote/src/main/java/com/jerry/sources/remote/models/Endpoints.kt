package com.jerry.sources.remote.models

import com.jerry.sources.remote.BuildConfig.baseurl

sealed class Endpoints(private val path: String) {

    private val baseUrl = baseurl

    val url: String
        get() = "$baseUrl$path"

    data class GetCurrentWeather(
        val query: String,
        val apikey: String
    ) : Endpoints("/current.json?q=$query&key=$apikey")

}