package com.jerry.data.mapper

import com.jerry.data.models.LocationData
import com.jerry.domain.models.LocationDomain

fun LocationData.toDomain() = LocationDomain(
    latitude = this.latitude,
    longitude = this.longitude
)

fun LocationDomain.toData() = LocationData(
    latitude = this.latitude,
    longitude = this.longitude
)