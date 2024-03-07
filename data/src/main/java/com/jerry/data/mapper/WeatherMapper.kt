package com.jerry.data.mapper

import com.jerry.domain.models.Condition
import com.jerry.domain.models.Current
import com.jerry.domain.models.Day
import com.jerry.domain.models.Forecast
import com.jerry.domain.models.ForecastDay
import com.jerry.domain.models.Hour
import com.jerry.domain.models.Location
import com.jerry.domain.models.WeatherDomain
import com.jerry.sources.remote.models.WeatherDTO

fun WeatherDTO.ResponseBody.toDomain() = WeatherDomain(
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
        ),
        wind_mph = this.current.wind_mph,
        wind_kph = this.current.wind_kph,
        wind_degree = this.current.wind_degree,
        wind_dir = this.current.wind_dir,
        pressure_mb = this.current.pressure_mb,
        pressure_in = this.current.pressure_in,
        precip_mm = this.current.precip_mm,
        precip_in = this.current.precip_in,
        humidity = this.current.humidity,
        cloud = this.current.cloud,
        feelslike_c = this.current.feelslike_c,
        feelslike_f = this.current.feelslike_f,
        vis_km = this.current.vis_km,
        vis_miles = this.current.vis_miles,
        uv = this.current.uv,
        gust_mph = this.current.gust_mph,
        gust_kph = this.current.gust_kph
    ),
    forecast = Forecast(
        forecastday = this.forecast.forecastday.map { forecastDay ->
            ForecastDay(
                date = forecastDay.date,
                date_epoch = forecastDay.date_epoch,
                day = Day(
                    maxtemp_c = forecastDay.day.maxtemp_c,
                    maxtemp_f = forecastDay.day.maxtemp_f,
                    mintemp_c = forecastDay.day.mintemp_c,
                    mintemp_f = forecastDay.day.mintemp_f,
                    avgtemp_c = forecastDay.day.avgtemp_c,
                    avgtemp_f = forecastDay.day.avgtemp_f,
                    maxwind_mph = forecastDay.day.maxwind_mph,
                    maxwind_kph = forecastDay.day.maxwind_kph,
                    totalprecip_mm = forecastDay.day.totalprecip_mm,
                    totalprecip_in = forecastDay.day.totalprecip_in,
                    totalsnow_cm = forecastDay.day.totalsnow_cm,
                    avgvis_km = forecastDay.day.avgvis_km,
                    avgvis_miles = forecastDay.day.avgvis_miles,
                    avghumidity = forecastDay.day.avghumidity,
                    daily_will_it_rain = forecastDay.day.daily_will_it_rain,
                    daily_chance_of_rain = forecastDay.day.daily_chance_of_rain,
                    daily_will_it_snow = forecastDay.day.daily_will_it_snow,
                    daily_chance_of_snow = forecastDay.day.daily_chance_of_snow,
                    condition = Condition(
                        text = this.current.condition.text,
                        icon = this.current.condition.icon,
                        code = this.current.condition.code
                    ),
                    uv = forecastDay.day.uv
                ),
                hour = forecastDay.hour.map { hour ->
                    Hour(
                        time_epoch = hour.time_epoch,
                        time = hour.time,
                        temp_c = hour.temp_c,
                        temp_f = hour.temp_f,
                        is_day = hour.is_day,
                        condition = Condition(
                            text = hour.condition.text,
                            icon = hour.condition.icon,
                            code = hour.condition.code
                        ),
                        wind_mph = hour.wind_mph,
                        wind_kph = hour.wind_kph,
                        wind_degree = hour.wind_degree,
                        wind_dir = hour.wind_dir,
                        pressure_mb = hour.pressure_mb,
                        pressure_in = hour.pressure_in,
                        precip_mm = hour.precip_mm,
                        precip_in = hour.precip_in,
                        snow_cm = hour.snow_cm,
                        humidity = hour.humidity,
                        cloud = hour.cloud,
                        feelslike_c = hour.feelslike_c,
                        feelslike_f = hour.feelslike_f,
                        windchill_c = hour.windchill_c,
                        windchill_f = hour.windchill_f,
                        heatindex_c = hour.heatindex_c,
                        heatindex_f = hour.heatindex_f,
                        dewpoint_c = hour.dewpoint_c,
                        dewpoint_f = hour.dewpoint_f,
                        will_it_rain = hour.will_it_rain,
                        chance_of_rain = hour.chance_of_rain,
                        will_it_snow = hour.will_it_snow,
                        chance_of_snow = hour.chance_of_snow,
                        vis_km = hour.vis_km,
                        vis_miles = hour.vis_miles,
                        gust_mph = hour.gust_mph,
                        gust_kph = hour.gust_kph,
                        uv = hour.uv,
                        short_rad = hour.short_rad,
                        diff_rad = hour.diff_rad
                    )
                }
            )
        }
    )
)
