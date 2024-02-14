package com.jerry.data.mapper

import com.jerry.domain.models.Condition
import com.jerry.domain.models.Current
import com.jerry.domain.models.Location
import com.jerry.domain.models.WeatherDomain
import com.jerry.sources.remote.models.CurrentWeatherDTO

fun WeatherDomain.toResponseBody() = CurrentWeatherDTO.ResponseBody(
    location = CurrentWeatherDTO.Location(
        name = this.location.name,
        region = this.location.region,
        country = this.location.country,
        lat = this.location.lat,
        lon = this.location.lon,
        tz_id = this.location.tz_id,
        localtime_epoch = this.location.localtime_epoch,
        localtime = this.location.localtime
    ),
    current = CurrentWeatherDTO.Current(
        last_updated_epoch = this.current.last_updated_epoch,
        last_updated = this.current.last_updated,
        temp_c = this.current.temp_c,
        temp_f = this.current.temp_f,
        is_day = this.current.is_day,
        condition = CurrentWeatherDTO.Condition(
            text = this.current.condition.text,
            icon = this.current.condition.icon,
            code = this.current.condition.code
        )
    )
)

fun CurrentWeatherDTO.ResponseBody.toDomain() = WeatherDomain(
    location = Location(
        name = this.location.name,
        region = this.location.region,
        country = this.location.country,
        lat = this.location.lat,
        lon = this.location.lon,
        tz_id = this.location.tz_id,
        localtime_epoch = this.location.localtime_epoch,
        localtime = this.location.localtime
    ),
    current = Current(
        last_updated_epoch = this.current.last_updated_epoch,
        last_updated = this.current.last_updated,
        temp_c = this.current.temp_c,
        temp_f = this.current.temp_f,
        is_day = this.current.is_day,
        condition = Condition(
            text = this.current.condition.text,
            icon = this.current.condition.icon,
            code = this.current.condition.code
        )
    )
)
