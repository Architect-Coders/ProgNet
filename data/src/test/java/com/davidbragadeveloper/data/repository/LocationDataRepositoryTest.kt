package com.davidbragadeveloper.data.repository

import arrow.core.Try
import com.davidbragadeveloper.data.source.LocationDataSource
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.usecases.repositories.LocationRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationDataRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var locationRepository: LocationRepository

    @Before
    fun setUp() {
        locationRepository = LocationDataRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `should findLastLocation returns null when coarse permission not granted`() {
        runBlocking {

            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(false)

            val location = locationRepository.findLastLocation()

            assertEquals(Try{null}, location)
        }
    }

    @Test
    fun `should findLastLocation returns location when permission granted`() {
        runBlocking {

            val location =  ProgNetLocation(
                latitude = 0.5,
                longitude = 1.7
            )
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLastLocation()).thenReturn(Try{location})

            val locationResult = locationRepository.findLastLocation()

            assertEquals(Try{location}, locationResult)

        }
    }
}

