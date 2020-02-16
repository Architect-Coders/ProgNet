package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.testshared.mockedAlbum
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import kotlinx.coroutines.runBlocking
import com.davidbragadeveloper.testshared.mockedSimpleAlbum
import com.davidbragadeveloper.usecases.repositories.LocationRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert


@RunWith(MockitoJUnitRunner::class)
class DicoverAlbumsTest{

    @Mock
    lateinit var albumsRepository: AlbumsRepository

    lateinit var dicoverAlbums: DiscoverAlbums

    @Before
    fun setUp(){
        dicoverAlbums = buildDiscoverAlbumsUseCase(repository = albumsRepository)
    }

    @Test
    fun `should GetAlbumById function invokes repository`(){
        runBlocking{
            val albumReponse = Try{ listOf(mockedAlbum.copy(id = 1))}
            whenever(albumsRepository.dicoverAlbums()).thenReturn(albumReponse)

            val result = dicoverAlbums.invoke()

            Assert.assertEquals(albumReponse, result)

        }
    }
}

