package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import com.davidbragadeveloper.testshared.mockedAlbum
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetAlbumByIdTest {

    @Mock
    lateinit var albumsRepository: AlbumsRepository

    lateinit var getAlbumById: GetAlbumById

    @Before
    fun setUp(){
        getAlbumById = buildGetAlbumByIdUseCase(repository = albumsRepository)
    }

    @Test
    fun `should GetAlbumById function invokes repository`(){
        runBlocking{
            val albumReponse = Try{mockedAlbum.copy(id = 1)}
            whenever(albumsRepository.getAlbumById(albumId = 1)).thenReturn(albumReponse)

            val result = getAlbumById(1)

            Assert.assertEquals(albumReponse, result)

        }
    }
}

