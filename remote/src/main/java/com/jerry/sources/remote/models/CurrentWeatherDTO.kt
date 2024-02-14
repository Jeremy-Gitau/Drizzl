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
        val lat: Double,
        val lon: Double,
        val tz_id: String,
        val localtime_epoch: Long,
        val localtime: String
    )

    @Serializable
    data class Current(
        val last_updated_epoch: Long,
        val last_updated: String,
        val temp_c: Double,
        val temp_f: Double,
        val is_day: Long,
        val condition: Condition
    )

    @Serializable
    data class Condition(
        val text: String,
        val icon: String,
        val code: Long
    )

}