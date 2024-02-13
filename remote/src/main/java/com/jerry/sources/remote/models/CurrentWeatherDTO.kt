package com.jerry.sources.remote.models

import kotlinx.serialization.Serializable

sealed class CurrentWeatherDTO {

    @Serializable
    data class ResponseBody(
        val location: Location,
        val current: Current
    )

    @Serializable
    data class Location(
        val name: String,
        val region: String,
        val country: String,
        val lat: Float,
        val lon: Float,
        val tz_id: String,
        val localtime_epoch: Int,
        val localtime: String
    )

    @Serializable
    data class Current(
        val last_updated_epoch: Int,
        val last_updated: String,
        val temp_c: Float,
        val temp_f: Float,
        val is_day: Int,
        val condition: Condition
    )

    @Serializable
    data class Condition(
        val text: String,
        val icon: String,
        val code: Int
    )

}