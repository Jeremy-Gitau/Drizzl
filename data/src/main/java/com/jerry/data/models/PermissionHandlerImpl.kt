package com.jerry.data.models

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jerry.data.mapper.toDomain
import com.jerry.domain.models.DataResult
import com.jerry.domain.models.LocationDomain
import com.jerry.domain.repository.PermissionHandler
import javax.inject.Inject
import com.google.android.gms.location.LocationServices as locationServices


data class LocationData(
    val latitude: Double,
    val longitude: Double
)

class PermissionHandlerImpl @Inject constructor(private val context: Activity) : PermissionHandler {

    companion object {

        private const val requestCode = 1
        private val permissionGranted = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }


    override suspend fun getCurrentLocation(): DataResult<LocationDomain> {

        if (isPermissionGranted()) {
            val location = fetchLocation()
            return if (location != null) {
                DataResult.Success(LocationData(location.latitude, location.longitude).toDomain())
            } else {
                DataResult.Error("Failed to fetch location")
            }
        } else {
            return try {
                ActivityCompat.requestPermissions(context, permissionGranted, requestCode)

                DataResult.Error("granting permissions")
            } catch (e: Exception) {
                DataResult.Error("Permission Denied")
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        for (permission in permissionGranted) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation(): LocationData? {
        val fusedLocationClient = locationServices.getFusedLocationProviderClient(context)

        return try {
            val location = fusedLocationClient.lastLocation.result
            LocationData(
                location.latitude,
                location.longitude
            )
        } catch (e: Exception) {
            null
        }

    }
}