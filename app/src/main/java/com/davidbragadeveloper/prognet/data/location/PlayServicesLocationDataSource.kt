package com.davidbragadeveloper.prognet.data.location

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import arrow.core.Try
import com.davidbragadeveloper.data.source.LocationDataSource
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.prognet.data.mappers.toDomain
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Try<ProgNetLocation?> =
        suspendCancellableCoroutine { continuation ->
           fusedLocationClient.lastLocation
               .addOnCompleteListener {
                    continuation.resume(
                        Try{ it.result?.toDomain()}
                    )
                }
        }


}




