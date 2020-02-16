package com.davidbragadeveloper.data.repository

import arrow.core.Try
import com.davidbragadeveloper.data.source.LocationDataSource
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.usecases.repositories.LocationRepository

class LocationDataRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
): LocationRepository {

    override suspend fun findLastLocation(): Try<ProgNetLocation?> =
        if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastLocation()
        } else {
            Try.just(null)
        }
}

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}