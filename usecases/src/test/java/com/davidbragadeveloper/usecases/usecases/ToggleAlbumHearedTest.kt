package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import arrow.core.getOrElse
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.testshared.mockedAlbum
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository
import com.davidbragadeveloper.usecases.repositories.LocationRepository
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ToggleAlbumHearedTest{
    @Mock
    lateinit var albumsRepository: AlbumsRepository
    @Mock
    lateinit var locationRepository: LocationRepository

    lateinit var toggleAlbumHeared: ToggleAlbumHeared

    @Before
    fun setUp(){
        toggleAlbumHeared = buildToggleAlbumHearedUseCase(
            albumsRepository= albumsRepository,
            locationRepository = locationRepository
        )
    }

    @Test
    fun `should toggleAlbumHeared function invokes locaiton repository`(){
        runBlocking{
            val newLocation = ProgNetLocation(latitude= 56.5, longitude = -43.7)
            whenever(locationRepository.findLastLocation()).thenReturn(Try.Success(newLocation))
            toggleAlbumHeared(mockedAlbum)
            verify(locationRepository).findLastLocation()
        }
    }

    @Test
    fun `should toggleAlbumHeared function invokes Albums repository whith location repository changes`(){
        runBlocking{
            val newLocation = ProgNetLocation(latitude= 56.5, longitude = -43.7)
            whenever(locationRepository.findLastLocation()).thenReturn(Try.Success(newLocation))
            toggleAlbumHeared(mockedAlbum)
            verify(albumsRepository).toggleAlbumHeared(
                mockedAlbum.copy(heared = !mockedAlbum.heared,
                    location = newLocation)
            )
        }
    }


    @Test
    fun `heared album becomes unheared`() {
        runBlocking {
            val newLocation = ProgNetLocation(latitude= 56.5, longitude = -43.7)
            whenever(locationRepository.findLastLocation()).thenReturn(Try.Success(newLocation))

            val album = mockedAlbum.copy(heared = true)

            val result = toggleAlbumHeared.invoke(album)

            verify(albumsRepository).toggleAlbumHeared(
                mockedAlbum.copy(heared = false,
                    location = newLocation)
            )
        }
    }

    @Test
    fun `unHeared album becomes heared`() {
        runBlocking {
            val newLocation = ProgNetLocation(latitude= 56.5, longitude = -43.7)
            whenever(locationRepository.findLastLocation()).thenReturn(Try.Success(newLocation))
            val album = mockedAlbum.copy(heared = false)

            val result = toggleAlbumHeared.invoke(album)

            verify(albumsRepository).toggleAlbumHeared(
                mockedAlbum.copy(heared = true,
                    location = newLocation)
            )
        }
    }
}

