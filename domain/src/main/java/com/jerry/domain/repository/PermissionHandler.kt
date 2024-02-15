package com.jerry.domain.repository

import com.jerry.domain.models.DataResult
import com.jerry.domain.models.LocationDomain

interface PermissionHandler {

    suspend fun getCurrentLocation(): DataResult<LocationDomain>
}